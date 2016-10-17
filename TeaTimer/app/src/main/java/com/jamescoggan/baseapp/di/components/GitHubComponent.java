package com.jamescoggan.baseapp.di.components;

import com.jamescoggan.baseapp.di.modules.GitHubModule;
import com.jamescoggan.baseapp.di.scopes.UserScope;
import com.jamescoggan.baseapp.fragments.HomeFragment;

import dagger.Component;

@UserScope
@Component(dependencies = NetComponent.class, modules = GitHubModule.class)
public interface GitHubComponent {
    void inject(HomeFragment homeFragment);
}
