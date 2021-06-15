package com.bagus.busreservationsystem.rest;

import com.bagus.busreservationsystem.models.User;
import com.bagus.busreservationsystem.models.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("auth/register")
    Call<User> createUser(@Body UserRegister register);
}
