package com.bagus.busreservationsystem.rest;

import com.bagus.busreservationsystem.models.AuthLogin;
import com.bagus.busreservationsystem.models.Stop;
import com.bagus.busreservationsystem.models.Ticket;
import com.bagus.busreservationsystem.models.TicketReservation;
import com.bagus.busreservationsystem.models.TripSchedule;
import com.bagus.busreservationsystem.models.User;
import com.bagus.busreservationsystem.models.UserLogin;
import com.bagus.busreservationsystem.models.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("auth/register")
    Call<User> createUser(@Body UserRegister register);

    @POST("auth/login")
    Call<AuthLogin> login(@Body UserLogin userLogin);

    @GET("stop")
    Call<List<Stop>> getStops();

    @GET("ticket")
    Call<List<Ticket>> getTickets(@Query("passengerId") String id);

    @POST("ticket")
    Call<Ticket> createTicket(@Body TicketReservation ticketReservation);

    @GET("tripSchedule")
    Call<List<TripSchedule>> getTripSchedules();

    @GET("tripSchedule")
    Call<List<TripSchedule>> getTripSchedulesParam(@Query("destStopId") Integer destStopId, @Query("from") String from, @Query("sourceStopId") Integer sourceStopId, @Query("to") String to);

    @GET("tripSchedule/{id}")
    Call<TripSchedule> getTripSchedule(@Path("id") String id);
}
