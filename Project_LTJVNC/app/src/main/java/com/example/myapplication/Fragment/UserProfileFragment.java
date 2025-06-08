package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Api.BookingApi;
import com.example.myapplication.Api.UserApi;
import com.example.myapplication.Model.Booking;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileFragment extends Fragment {

    private CircleImageView imgAvatar;
    private TextView txtName, txtEmail, txtGender, txtPhoneNumber, txtBookingCount;
    private Button btnLogout;
    Retrofit retrofit;

    private static final String TAG = "UserProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Ánh xạ view
        imgAvatar = rootView.findViewById(R.id.imgAvatar);
        txtName = rootView.findViewById(R.id.txtName);
        txtEmail = rootView.findViewById(R.id.txtEmail);
        txtGender = rootView.findViewById(R.id.txtGender);
        txtPhoneNumber = rootView.findViewById(R.id.txtPhoneNumber);
        txtBookingCount = rootView.findViewById(R.id.txtBookingCount);
        btnLogout = rootView.findViewById(R.id.btnLogout);

        retrofit = RetrofitClientInstance.getRetrofitInstance();

        // Tạo id_User giả
        Long id_user = 1L;

        // Gọi API lấy thông tin user
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.getUserById(id_user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    txtName.setText(user.getUserName());
                    txtEmail.setText(user.getEmail());
                    int gender = user.getGender();
                    String genderText = (gender == 1) ? "Nam" : "Nữ"; // logic gender
                    txtGender.setText(genderText);
                    txtPhoneNumber.setText(user.getPhoneNumber());
                } else {
                    txtName.setText("Không tìm thấy người dùng.");
                    Log.e(TAG, "Response failed: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "API error", t);
                txtName.setText("Lỗi: " + t.getMessage());
            }
        });

        // Gọi API lấy số lượng booking của user
        BookingApi bookingApi = retrofit.create(BookingApi.class);
        bookingApi.getBookingsByUserId(id_user).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Booking> listBooking = response.body();
                    int count = listBooking.size();
                    txtBookingCount.setText(String.valueOf(count));
                } else {
                    txtBookingCount.setText("Không tìm thấy tổng số đơn.");
                    Log.e(TAG, "Response failed: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                txtBookingCount.setText("Lỗi: " + t.getMessage());
                Log.e(TAG, "API error", t);
            }
        });

        // Xử lý nút đăng xuất
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            // Ví dụ: quay về màn hình đăng nhập
            // Intent intent = new Intent(getActivity(), LoginActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(intent);
        });

        return rootView;
    }
}
