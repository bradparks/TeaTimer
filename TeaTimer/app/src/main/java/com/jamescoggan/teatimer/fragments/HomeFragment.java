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
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jamescoggan.teatimer.R;
import com.jamescoggan.teatimer.Utils.PrimaryKeyFactory;
import com.jamescoggan.teatimer.Views.TimeDialogView;
import com.jamescoggan.teatimer.adapters.RecyclerItemClickListener;
import com.jamescoggan.teatimer.adapters.TimerAdapter;
import com.jamescoggan.teatimer.baseclasses.BaseFragment;
import com.jamescoggan.teatimer.models.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import timber.log.Timber;

public class HomeFragment extends BaseFragment {

    private Realm realm;
    RealmResults<Timer> timers;

    TimerAdapter timerAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.home_timer_list)
    RecyclerView timerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);

        assert rootView != null;
        ButterKnife.bind(this, rootView);

        realm = Realm.getDefaultInstance();

        timers = realm.where(Timer.class).findAll().sort("id", Sort.DESCENDING);

        timers.addChangeListener(this::updateList);

        mLayoutManager = new LinearLayoutManager(getActivity());

        timerAdapter = new TimerAdapter(timers);
        timerList.setHasFixedSize(true);
        timerList.setAdapter(timerAdapter);
        timerList.setLayoutManager(mLayoutManager);
        timerList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), (view, position) -> onItemSelect(position))
        );


        return rootView;
    }

    private void onItemSelect(int position) {
        Timber.d("Item clicked %d", position);
        Timer timer = timerAdapter.getItemAtPosition(position);
        new CountDownTimer(timer.getTime(), 1000) {

            public void onTick(long millisUntilFinished) {
                Timber.d("Seconds remaining %d", millisUntilFinished / 1000);
            }

            public void onFinish() {
                Timber.d("Finished");
            }
        }.start();
    }

    private void updateList(RealmResults<Timer> repositories) {
        timerAdapter.setItems(timers);
    }

    @OnClick(R.id.home_fab)
    public void addTimer() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_time_picker, null);
        final TimeDialogView timeDialogView = new TimeDialogView(view);
        new MaterialDialog.Builder(getActivity())
                .backgroundColorRes(R.color.dialog_background_color)
                .titleColorRes(R.color.dialog_title_color)
                .title(R.string.home_dialog_time_title)
                .customView(view, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> saveTimer(timeDialogView.getTimer()))
                .show();
    }

    private void saveTimer(Timer timer) {
        realm.beginTransaction();
        Timer dbTimer = realm.createObject(Timer.class, PrimaryKeyFactory.getInstance().nextKey(Timer.class));
        dbTimer.setName(timer.getName());
        dbTimer.setTime(timer.getTime());
        realm.copyToRealm(dbTimer);
        realm.commitTransaction();
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
