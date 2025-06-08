package com.example.myapplication.old_activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Api.RoomApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.myapplication.Api.ReviewApi;
import com.example.myapplication.Model.Homestay;
import com.example.myapplication.Model.Room;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

public class RoomDetailActivity extends AppCompatActivity {

    private TextView textRoomName, textAddress, textIntro, textAmenities,txtTypeRoom,txtPrice,txtRate;
    private ImageView imageRoom;
    private Button btnBook, btnSave;
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    Intent intent;

    String address;
    Double rate=-1.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_room_detail); // đổi "your_layout_name" thành tên file XML của bạn (ví dụ: activity_room_detail)
        // Ánh xạ View
        textRoomName = findViewById(R.id.textRoomName);
        textAddress = findViewById(R.id.textAddress);
        textIntro = findViewById(R.id.textIntro);
        textAmenities = findViewById(R.id.textAmenities);
        txtTypeRoom = findViewById(R.id.txtTypeRoom);
        txtPrice = findViewById(R.id.txtPrice);
        imageRoom = findViewById(R.id.imageRoom);
        btnBook = findViewById(R.id.btnBook);
        btnSave = findViewById(R.id.btnSave);
        txtRate = findViewById(R.id.txtRate);

        // Nhận Room object từ Intent
//        Room room = getIntent().getParcelableExtra("room_data");
        Room room = getIntent().getParcelableExtra("room");
        if (room != null) {
            displayRoomInfo(room);
        } else {
            Toast.makeText(this, "Không có dữ liệu phòng", Toast.LENGTH_SHORT).show();
            finish(); // đóng Activity nếu không có dữ liệu
        }
        intent = new Intent(RoomDetailActivity.this, BookingActivity.class);
        // Xử lý nút đặt phòng
        btnBook.setOnClickListener(v -> {
            intent.putExtra("rate", rate);
            intent.putExtra("room_data", room);
            intent.putExtra("address", address);
            startActivity(intent);
        });

        // Xử lý nút lưu (ví dụ chỉ hiển thị Toast)
        btnSave.setOnClickListener(v -> {
            Toast.makeText(RoomDetailActivity.this, "Đã lưu phòng", Toast.LENGTH_SHORT).show();
            // Bạn có thể thêm code lưu vào CSDL hoặc SharedPreferences ở đây
//            Intent intent = new Intent(RoomDetailActivity.this, RoomHistory_Activity.class);
//            startActivity(intent);
        });

    }

    private void displayRoomInfo(Room room) {
        //Lấy homestay
        RoomApi roomApi = retrofit.create(RoomApi.class);
        Call<Homestay> call = roomApi.getHomestayById(room.getRoomId());
        call.enqueue(new Callback<Homestay>() {
            @Override
            public void onResponse(Call<Homestay> call, Response<Homestay> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Homestay homestay = response.body();
                    // Xử lý địa chỉ
                    address = homestay.getWard()+", "+homestay.getDistrict()+", "+homestay.getProvince();
                    // TODO: hiển thị thông tin room trên UI
                    textAddress.setText(address);
                    textIntro.setText(homestay.getDescription());
                    setRate(homestay.getHomestayId());
                    Log.d(TAG, "Homestay name: " + homestay.getName() );
                } else {
                    Toast.makeText(RoomDetailActivity.this, "Không tìm thấy Homestay", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Homestay> call, Throwable t) {
                Toast.makeText(RoomDetailActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "API error", t);
            }
        });
        textRoomName.setText(room.getRoomName());
        textAmenities.setText("WiFi miễn phí\nĐưa đón sân bay");
        // Ảnh phòng tạm thời để ảnh mặc định, bạn có thể load ảnh từ URL nếu có
        imageRoom.setImageResource(android.R.drawable.ic_menu_gallery);
        txtTypeRoom.setText("Loại phòng: "+room.getRoomType());
        txtPrice.setText("Giá phòng: "+room.getPrice()+" VNĐ");
    }

    private void setRate(long homestayId){
        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<Double> call = reviewApi.getAverageRate(homestayId);
        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()) {
                    double average = response.body();
                    Log.d("API", "Average rate: " + average);
                    // TODO: hiển thị trên UI
                    txtRate.setText(average + "⭐");
                    rate = average;
                } else {
                    Log.e("API", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.e("API", "API error", t);
            }
        });
    }
}
