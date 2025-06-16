package com.example.login_logout.newui.Api;

import com.example.login_logout.newui.Model.Booking;
import com.example.login_logout.newui.Model.BookingBill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingApi {
    @POST("api/bookings")
    Call<Booking> createBooking(@Body Booking booking);

    @GET("api/booking-history/user/{userId}")
    Call<List<BookingBill>> getBookingHistoryByUserId(@Path("userId") Long userId);
}