package com.jamescoggan.baseapp.di.components;


import android.content.SharedPreferences;

import com.jamescoggan.baseapp.di.modules.AppModule;
import com.jamescoggan.baseapp.di.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@SuppressWarnings("unused")
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();

    OkHttpClient okHttpClient();

    SharedPreferences sharedPreferences();
}