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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamescoggan.baseapp.R;
import com.jamescoggan.baseapp.baseclasses.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);

        assert rootView != null;
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.home_fab)
    public void onFabClick() {

    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_home;
    }
}
