package com.example.login_logout.newui.old_activity;

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

import com.example.login_logout.R;
import com.example.login_logout.newui.Api.ReviewApi;
import com.example.login_logout.newui.Api.RoomApi;
import com.example.login_logout.newui.Model.Homestay;
import com.example.login_logout.newui.Model.Room;
import com.example.login_logout.newui.retrofit.RetrofitClientInstance;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomDetailActivity extends AppCompatActivity {

    private TextView textRoomName, textAddress, textIntro, textAmenities, txtTypeRoom, txtPrice, txtRate;
    private ImageView imageRoom, btnBack;
    private Button btnBook;
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    Intent intent;

    String address;
    Double rate = -1.0;

    // Mảng chứa các ID tài nguyên ảnh từ home1 đến home18
    private final int[] homeImages = {
            R.drawable.home1, R.drawable.home2, R.drawable.home3, R.drawable.home4,
            R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8,
            R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12,
            R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16,
            R.drawable.home17, R.drawable.home18
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_room_detail);

        // Ánh xạ View
        textRoomName = findViewById(R.id.textRoomName);
        textAddress = findViewById(R.id.textAddress);
        textIntro = findViewById(R.id.textIntro);
        textAmenities = findViewById(R.id.textAmenities);
        txtTypeRoom = findViewById(R.id.txtTypeRoom);
        txtPrice = findViewById(R.id.txtPrice);
        imageRoom = findViewById(R.id.imageRoom);
        btnBook = findViewById(R.id.btnBook);
        btnBack = findViewById(R.id.btnBack);
        txtRate = findViewById(R.id.txtRate);

        // Xử lý sự kiện nút back
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại và quay lại màn hình trước
        });

        // Nhận Room object từ Intent
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
    }

    private void displayRoomInfo(Room room) {
        // Lấy homestay
        RoomApi roomApi = retrofit.create(RoomApi.class);
        Call<Homestay> call = roomApi.getHomestayById(room.getRoomId());
        call.enqueue(new Callback<Homestay>() {
            @Override
            public void onResponse(Call<Homestay> call, Response<Homestay> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Homestay homestay = response.body();
                    // Xử lý địa chỉ
                    address = homestay.getWard() + ", " + homestay.getDistrict() + ", " + homestay.getProvince();
                    // TODO: hiển thị thông tin room trên UI
                    textAddress.setText(address);
                    textIntro.setText(homestay.getDescription());
                    setRate(homestay.getHomestayId());
                    Log.d(TAG, "Homestay name: " + homestay.getName());
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
        textAmenities.setText("WiFi miễn phí\nĐưa đón sân bay\nBuffet\nPhục vụ tận phòng");
        // Gán ảnh ngẫu nhiên từ home1 đến home18
        int randomImageIndex = getRandomImageIndex();
        imageRoom.setImageResource(homeImages[randomImageIndex]);

        if(room.getRoomType()==1) txtTypeRoom.setText("Loại phòng: Phòng đơn");
        else if(room.getRoomType()==2) txtTypeRoom.setText("Loại phòng: Phòng đôi");
        else txtTypeRoom.setText("Loại phòng: "+room.getRoomType());

        txtPrice.setText("Giá phòng: " + room.getPrice() + " USD");
    }

    private void setRate(long homestayId) {
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

    // Hàm tạo chỉ số ngẫu nhiên từ 0 đến 17 (tương ứng với 18 ảnh)
    private int getRandomImageIndex() {
        Random random = new Random();
        return random.nextInt(homeImages.length); // Tạo số từ 0 đến 17
    }
}