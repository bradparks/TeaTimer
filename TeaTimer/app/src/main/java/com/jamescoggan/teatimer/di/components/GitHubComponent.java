package com.jamescoggan.teatimer.di.components;

import com.jamescoggan.teatimer.di.modules.GitHubModule;
import com.jamescoggan.teatimer.di.scopes.UserScope;
import com.jamescoggan.teatimer.fragments.HomeFragment;

import dagger.Component;

@UserScope
@Component(dependencies = NetComponent.class, modules = GitHubModule.class)
public interface GitHubComponent {
    void inject(HomeFragment homeFragment);
}
