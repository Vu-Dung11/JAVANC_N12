package com.example.login_logout.newui.old_activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_logout.R;
import com.example.login_logout.newui.Adapter.Room_Adapter;
import com.example.login_logout.newui.Api.RoomApi;
import com.example.login_logout.newui.Model.Room;
import com.example.login_logout.newui.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Room_List_Activity extends AppCompatActivity {

    private RecyclerView recyclerRoom;
    private Room_Adapter adapter;
    private List<Room> roomList;


    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_room);



        btnBack = findViewById(R.id.btnBack);
        recyclerRoom = findViewById(R.id.recyclerRoomList);
        recyclerRoom.setLayoutManager(new LinearLayoutManager(this));

        // Dữ liệu giả lập
        roomList = new ArrayList<>();
        // Gọi API với homestayId
        long homestayId = getIntent().getLongExtra("homestayId", -1L);
        Log.d("Room_List_Activity", "Received homestayId: " + homestayId);
        
        if (homestayId == -1L) {
            Toast.makeText(this, "Không tìm thấy thông tin homestay", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // Xử lý sự kiện nút back
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại và quay lại màn hình trước
        });

        RoomApi roomApi = RetrofitClientInstance.getRetrofitInstance().create(RoomApi.class);
        Call<List<Room>> call = roomApi.getRoomsByHomestayId(homestayId);

        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Room> roomList = response.body();
                    adapter = new Room_Adapter(Room_List_Activity.this, roomList);
                    recyclerRoom.setAdapter(adapter);
                } else {
                    Toast.makeText(Room_List_Activity.this, "Không lấy được danh sách phòng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(Room_List_Activity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                Log.e("Room_List_Activity","Lỗi kết nốt Server", t);
            }
        });

        adapter = new Room_Adapter(this, roomList);
        recyclerRoom.setAdapter(adapter);
    }
}
