package com.jamescoggan.teatimer.network.interfaces;

import com.jamescoggan.teatimer.models.Repository;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubApiInterface {
    @GET("/users/{user}/repos")
    Observable<List<Repository>> getRepository(@Path("user") String userName);

}