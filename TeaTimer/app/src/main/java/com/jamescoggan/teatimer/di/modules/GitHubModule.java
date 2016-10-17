package com.jamescoggan.teatimer.di.modules;

import com.jamescoggan.teatimer.di.scopes.UserScope;
import com.jamescoggan.teatimer.network.interfaces.GitHubApiInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class GitHubModule {

    @Provides
    @UserScope
    GitHubApiInterface providesGitHubInterface(Retrofit retrofit) {
        return retrofit.create(GitHubApiInterface.class);
    }
}
