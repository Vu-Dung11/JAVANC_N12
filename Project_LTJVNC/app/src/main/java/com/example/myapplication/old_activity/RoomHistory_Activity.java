package com.example.myapplication.old_activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Room_History_Adapter;
import com.example.myapplication.Api.BookingApi;
import com.example.myapplication.Api.RoomApi;
import com.example.myapplication.Model.Booking;
import com.example.myapplication.Model.BookingBill;
import com.example.myapplication.Model.Room;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomHistory_Activity extends AppCompatActivity {

    private RecyclerView recyclerViewRooms;
    private Room_History_Adapter roomAdapter;
    private List<BookingBill> bookingBills = new ArrayList<>();

    private BookingApi bookingApi;
    private RoomApi roomApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_room_history);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        bookingApi = retrofit.create(BookingApi.class);
        roomApi = retrofit.create(RoomApi.class);

        Long userId = 1L; // Có thể lấy từ SharedPreferences

        bookingApi.getBookingsByUserId(userId).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Booking> bookingList = response.body();
                    Log.d("RoomHistory", "Số lượng booking nhận được: " + bookingList.size());

                    if (bookingList.isEmpty()) {
                        runOnUiThread(() -> {
                            roomAdapter = new Room_History_Adapter(bookingBills);
                            recyclerViewRooms.setAdapter(roomAdapter);
                        });
                        return;
                    }

                    AtomicInteger processedCount = new AtomicInteger(0);
                    int totalBookings = bookingList.size();

                    for (Booking booking : bookingList) {
                        Long bookingId = booking.getBookingId();
                        Log.d("RoomHistory", "Booking nhận được: booking_id = " + bookingId);

                        if (bookingId == null) {
                            Log.e("RoomHistory", "BookingId null, bỏ qua booking: " + booking);

                            if (processedCount.incrementAndGet() == totalBookings) {
                                runOnUiThread(() -> {
                                    roomAdapter = new Room_History_Adapter(bookingBills);
                                    recyclerViewRooms.setAdapter(roomAdapter);
                                });
                            }
                            continue;
                        }

                        roomApi.getRoomsByBookingId(bookingId).enqueue(new Callback<List<Room>>() {
                            @Override
                            public void onResponse(Call<List<Room>> call, Response<List<Room>> roomResponse) {
                                if (roomResponse.isSuccessful() && roomResponse.body() != null) {
                                    List<Room> rooms = roomResponse.body();
                                    if (!rooms.isEmpty()) {
                                        Room room = rooms.get(0);
                                        if (room.getRoomId() != null) {
                                            Log.d("RoomHistory", "Lấy phòng thành công: RoomId = " + room.getRoomId() + ", RoomName = " + room.getRoomName());

                                            bookingBills.add(new BookingBill(
                                                    booking.getBookingId(),
                                                    room.getRoomId(),
                                                    room.getRoomName(),
                                                    booking.getCheckInDate(),
                                                    booking.getTotalPrice(),
                                                    booking.getDepositPrice()
                                            ));
                                        } else {
                                            Log.w("RoomHistory", "RoomId null, bỏ qua phòng cho BookingId = " + booking.getBookingId());
                                        }
                                    } else {
                                        Log.w("RoomHistory", "API trả về danh sách phòng rỗng cho BookingId = " + booking.getBookingId() + ", bỏ qua");
                                    }
                                } else {
                                    Log.e("RoomHistory", "API trả về lỗi hoặc body null cho BookingId = " + booking.getBookingId() +
                                            ". Code: " + roomResponse.code() + ", Message: " + roomResponse.message());
                                }

                                if (processedCount.incrementAndGet() == totalBookings) {
                                    runOnUiThread(() -> {
                                        roomAdapter = new Room_History_Adapter(bookingBills);
                                        recyclerViewRooms.setAdapter(roomAdapter);
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Room>> call, Throwable t) {
                                Log.e("RoomHistory", "Lỗi gọi API phòng cho BookingId = " + booking.getBookingId(), t);

                                if (processedCount.incrementAndGet() == totalBookings) {
                                    runOnUiThread(() -> {
                                        roomAdapter = new Room_History_Adapter(bookingBills);
                                        recyclerViewRooms.setAdapter(roomAdapter);
                                    });
                                }
                            }
                        });
                    }
                } else {
                    Log.e("RoomHistory", "Lỗi lấy danh sách booking: code = " + response.code() + ", message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Log.e("RoomHistory", "Lỗi gọi API booking", t);
            }
        });
    }
}
