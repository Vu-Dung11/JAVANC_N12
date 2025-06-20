package com.example.login_logout.newui.Api;

import com.example.login_logout.newui.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Long id);
}