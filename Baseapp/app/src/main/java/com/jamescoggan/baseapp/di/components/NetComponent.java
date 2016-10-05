package com.jamescoggan.baseapp.di.components;


import android.content.SharedPreferences;

import com.jamescoggan.baseapp.di.modules.AppModule;
import com.jamescoggan.baseapp.di.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed
    Retrofit retrofit();

    OkHttpClient okHttpClient();

    SharedPreferences sharedPreferences();
}