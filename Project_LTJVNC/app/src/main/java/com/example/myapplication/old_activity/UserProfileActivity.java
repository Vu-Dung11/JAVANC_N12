package com.example.myapplication.old_activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Api.BookingApi;
import com.example.myapplication.Api.UserApi;
import com.example.myapplication.Model.Booking;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    private CircleImageView imgAvatar;
    private TextView txtName, txtEmail, txtGender, txtPhoneNumber, txtBookingCount;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_profile); // đổi tên file XML nếu bạn dùng tên khác
        // Ánh xạ view
        imgAvatar = findViewById(R.id.imgAvatar);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtGender = findViewById(R.id.txtGender);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtBookingCount = findViewById(R.id.txtBookingCount);
        btnLogout = findViewById(R.id.btnLogout);

        //Tạo id_User giả
        Long id_user = 1L;
        //Gọi api trả về user qua id_user
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.getUserById(id_user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    txtName.setText(user.getUserName());
                    txtEmail.setText(user.getEmail());
                    int gender = user.getGender();
                    String genderText = (gender == 1) ? "Nam" : "Nữ"; // hoặc tùy logic bạn
                    txtGender.setText(genderText);
                    txtPhoneNumber.setText(user.getPhoneNumber());
                } else {
                    txtName.setText("Không tìm thấy người dùng.");
                    Log.e("API", "Response failed: " + response.code());

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API", "API error", t);
                txtName.setText("Lỗi: " + t.getMessage());
            }
        });

        BookingApi bookingApi = retrofit.create(BookingApi.class);
        bookingApi.getBookingsByUserId(id_user).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Booking> listBooking = response.body();
                    int count = listBooking.size();
                    txtBookingCount.setText(""+count);
                } else {
                    txtBookingCount.setText("Không tìm thấy tổng số đơn.");
                    Log.e("API", "Response failed: " + response.code());

                }
            }
            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                txtBookingCount.setText("Lỗi: " + t.getMessage());
                Log.e("API", "API error", t);

            }
        });

        // Xử lý nút đăng xuất
        btnLogout.setOnClickListener(view -> {
            Toast.makeText(UserProfileActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            // Ví dụ: quay về màn hình đăng nhập
//            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
        });
    }
}
