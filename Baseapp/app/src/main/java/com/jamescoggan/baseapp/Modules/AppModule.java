package com.jamescoggan.baseapp.Modules;

import com.jamescoggan.baseapp.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private BaseApplication mApplication;

    public AppModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    BaseApplication providesApplication() {
        return mApplication;
    }
}