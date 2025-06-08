package com.example.myapplication.Api;

import com.example.myapplication.Model.Homestay;
import com.example.myapplication.Model.Room;
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
