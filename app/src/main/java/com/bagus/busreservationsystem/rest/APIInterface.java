package com.bagus.busreservationsystem.rest;

import com.bagus.busreservationsystem.models.AuthLogin;
import com.bagus.busreservationsystem.models.Stop;
import com.bagus.busreservationsystem.models.Ticket;
import com.bagus.busreservationsystem.models.User;
import com.bagus.busreservationsystem.models.UserLogin;
import com.bagus.busreservationsystem.models.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("auth/register")
    Call<User> createUser(@Body UserRegister register);

    @POST("auth/login")
    Call<AuthLogin> login(@Body UserLogin userLogin);

    @GET("stop")
    Call<List<Stop>> getStops();

    @GET("ticket")
    Call<List<Ticket>> getTickets();
}
