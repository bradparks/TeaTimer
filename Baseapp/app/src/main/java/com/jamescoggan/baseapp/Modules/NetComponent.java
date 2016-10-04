package com.jamescoggan.baseapp.Modules;

import com.jamescoggan.baseapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
   void inject(MainActivity activity);
}