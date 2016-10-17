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
package com.jamescoggan.teatimer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamescoggan.teatimer.R;
import com.jamescoggan.teatimer.baseclasses.BaseFragment;
import com.jamescoggan.teatimer.models.Timer;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class HomeFragment extends BaseFragment {

    private Realm realm;
    RealmResults<Timer> timers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);

        assert rootView != null;
        ButterKnife.bind(this, rootView);

        realm = Realm.getDefaultInstance();

        timers = realm.where(Timer.class).findAll();

        timers.addChangeListener(this::updateList);

        return rootView;
    }

    private void updateList(RealmResults<Timer> repositories) {
        for (Timer timer : timers) {
            Timber.d("timer: %s", timer.getName());
        }
    }

    @OnClick(R.id.home_fab)
    public void addTimer() {

    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_home;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
