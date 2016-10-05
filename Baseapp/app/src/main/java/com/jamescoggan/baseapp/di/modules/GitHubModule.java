package com.jamescoggan.baseapp.di.modules;

import com.jamescoggan.baseapp.di.scopes.UserScope;
import com.jamescoggan.baseapp.network.interfaces.GitHubApiInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class GitHubModule {

    @Provides
    @UserScope
    public GitHubApiInterface providesGitHubInterface(Retrofit retrofit) {
        return retrofit.create(GitHubApiInterface.class);
    }
}
