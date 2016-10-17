/**
 * Created by James Coggan on 04/10/2016.
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
package com.jamescoggan.baseapp;

import android.app.Application;
import android.content.Context;

import com.jamescoggan.baseapp.Utils.PrimaryKeyFactory;
import com.jamescoggan.baseapp.di.components.DaggerGitHubComponent;
import com.jamescoggan.baseapp.di.components.DaggerNetComponent;
import com.jamescoggan.baseapp.di.components.GitHubComponent;
import com.jamescoggan.baseapp.di.components.NetComponent;
import com.jamescoggan.baseapp.di.modules.AppModule;
import com.jamescoggan.baseapp.di.modules.GitHubModule;
import com.jamescoggan.baseapp.di.modules.NetModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class BaseApplication extends Application {
    private NetComponent mNetComponent;
    private GitHubComponent mGitHubComponent;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.github.com"))
                .build();

        mGitHubComponent = DaggerGitHubComponent.builder()
                .netComponent(mNetComponent)
                .gitHubModule(new GitHubModule())
                .build();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
        PrimaryKeyFactory.getInstance().initialize(Realm.getDefaultInstance());
    }

    @SuppressWarnings("unused")
    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public GitHubComponent getGitHubComponent() {
        return mGitHubComponent;
    }

    // Used by leakcanary
    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
