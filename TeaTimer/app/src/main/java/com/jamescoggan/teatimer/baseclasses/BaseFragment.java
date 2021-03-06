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
package com.jamescoggan.teatimer.baseclasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamescoggan.teatimer.BaseApplication;
import com.squareup.leakcanary.RefWatcher;

public abstract class BaseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getViewResource(), container, false);
    }

    protected abstract int getViewResource();

    public BaseApplication getApp() {
        BaseActivity activity = (BaseActivity) getActivity();
        return ((BaseApplication) activity.getApplication());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
            refWatcher.watch(this);
        } catch (Exception ignored) {

        }
    }
}
