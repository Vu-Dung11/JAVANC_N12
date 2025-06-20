package com.example.login_logout.newui.Api;

import com.example.login_logout.newui.Model.Homestay;
import com.example.login_logout.newui.Model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RoomApi {
    @GET("/api/rooms/gethomestay/{id}")
    Call<Homestay> getHomestayById(@Path("id") long roomId);
    @GET("/api/rooms/booking/{bookingId}")
    Call<List<Room>> getRoomsByBookingId(@Path("bookingId") Long bookingId);

    @GET("/api/rooms/homestay/{homestayId}")
    Call<List<Room>> getRoomsByHomestayId(@Path("homestayId") Long homestayId);
}
