/**
 * Created by James Coggan on 09/10/2016.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jamescoggan.baseapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamescoggan.baseapp.R;
import com.jamescoggan.baseapp.Utils.PrimaryKeyFactory;
import com.jamescoggan.baseapp.baseclasses.BaseFragment;
import com.jamescoggan.baseapp.models.Repository;
import com.jamescoggan.baseapp.network.interfaces.GitHubApiInterface;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class HomeFragment extends BaseFragment {

    @Inject
    SharedPreferences mSharedPreferences;

    @Inject
    Retrofit mRetrofit;

    @Inject
    GitHubApiInterface mGitHubApiInterface;

    private Realm realm;
    private Subscription subscription;
    RealmResults<Repository> repos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);

        assert rootView != null;
        ButterKnife.bind(this, rootView);

        getApp().getGitHubComponent().inject(this);

        realm = Realm.getDefaultInstance();

        repos = realm.where(Repository.class).findAll();

        repos.addChangeListener(this::updateList);

        return rootView;
    }

    private void updateList(RealmResults<Repository> repositories) {
        for (Repository repository : repositories) {
            Timber.d("Repo: %s", repository.getName());
        }
    }

    @OnClick(R.id.home_fab)
    public void onFabClick() {
        subscription = mGitHubApiInterface.getRepository("codepath")
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNextRepository,
                        this::onErrorRepository,
                        this::onCompleteRepository);
    }

    private void onNextRepository(Repository repository) {
        Timber.d("Received repo %s", repository.getFullName());
        assert getView() != null;

        saveRepo(repository);

        Snackbar.make(getView(), "Data retrieved", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    // Todo: improve this
    private void saveRepo(Repository repository) {
        RealmResults<Repository> list = realm.where(Repository.class).equalTo("name", repository.getName()).findAll();

        // If we have object, Update
        if (list.size() > 0) {
            realm.beginTransaction();
            repository.setId(list.first().getId());
            realm.copyToRealmOrUpdate(repository);
            realm.commitTransaction();

        } else {
            // insert new value
            realm.beginTransaction();
            Repository dbRepo = realm.createObject(Repository.class, PrimaryKeyFactory.getInstance().nextKey(Repository.class));
            dbRepo.setName(repository.getName());
            dbRepo.setDescription(repository.getDescription());
            dbRepo.setFullName(repository.getFullName());
            realm.copyToRealm(dbRepo);
            realm.commitTransaction();
        }
    }

    private void onCompleteRepository() {
        Timber.d("Completed request");
    }

    private void onErrorRepository(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_home;
    }


    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
