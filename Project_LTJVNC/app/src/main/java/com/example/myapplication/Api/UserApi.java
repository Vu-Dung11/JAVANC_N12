package com.example.myapplication.Api;

import com.example.myapplication.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Long id);
}