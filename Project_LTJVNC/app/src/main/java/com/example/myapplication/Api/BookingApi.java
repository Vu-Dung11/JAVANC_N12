package com.example.myapplication.Api;

import com.example.myapplication.Model.Booking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingApi {
    @POST("api/bookings")
    Call<Booking> createBooking(@Body Booking booking);
    @GET("api/bookings/user/{userId}")
    Call<List<Booking>> getBookingsByUserId(@Path("userId") Long userId);


}
