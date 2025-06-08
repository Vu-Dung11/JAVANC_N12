package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Api.RoomApi;
import com.example.myapplication.Api.ReviewApi;
import com.example.myapplication.Model.Homestay;
import com.example.myapplication.Model.Room;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomDetailFragment extends Fragment {

    private TextView textRoomName, textAddress, textIntro, textAmenities, txtTypeRoom, txtPrice, txtRate;
    private ImageView imageRoom;
    private Button btnBook, btnSave;

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    private Room room;
    private String address;
    private Double rate = -1.0;

    // Key để nhận đối tượng Room
    private static final String ARG_ROOM = "room";

    public static RoomDetailFragment newInstance(Room room) {
        RoomDetailFragment fragment = new RoomDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ROOM, room);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomDetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_detail, container, false);

        // Ánh xạ view
        textRoomName = view.findViewById(R.id.textRoomName);
        textAddress = view.findViewById(R.id.textAddress);
        textIntro = view.findViewById(R.id.textIntro);
        textAmenities = view.findViewById(R.id.textAmenities);
        txtTypeRoom = view.findViewById(R.id.txtTypeRoom);
        txtPrice = view.findViewById(R.id.txtPrice);
        imageRoom = view.findViewById(R.id.imageRoom);
        btnBook = view.findViewById(R.id.btnBook);
        btnSave = view.findViewById(R.id.btnSave);
        txtRate = view.findViewById(R.id.txtRate);

        // Lấy room từ arguments
        if (getArguments() != null) {
            room = getArguments().getParcelable(ARG_ROOM);
        }

        if (room == null) {
            Toast.makeText(requireContext(), "Không có dữ liệu phòng", Toast.LENGTH_SHORT).show();
            // Có thể quay lại Fragment trước hoặc đóng app tuỳ tình huống
            requireActivity().getSupportFragmentManager().popBackStack();
            return view;
        }

        displayRoomInfo(room);

        btnBook.setOnClickListener(v -> {
            // Ở đây nếu bạn có Activity Booking thì gọi Intent
            // Nếu Booking cũng là Fragment thì gọi FragmentTransaction
            // Ví dụ mở Activity:
            /*
            Intent intent = new Intent(requireContext(), BookingActivity.class);
            intent.putExtra("rate", rate);
            intent.putExtra("room_data", room);
            intent.putExtra("address", address);
            startActivity(intent);
            */
            Toast.makeText(requireContext(), "Chức năng đặt phòng chưa implement", Toast.LENGTH_SHORT).show();
        });

        btnSave.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Đã lưu phòng", Toast.LENGTH_SHORT).show();
            // TODO: Lưu vào CSDL hoặc SharedPreferences
        });

        return view;
    }

    private void displayRoomInfo(Room room) {
        RoomApi roomApi = retrofit.create(RoomApi.class);
        Call<Homestay> call = roomApi.getHomestayById(room.getRoomId());
        call.enqueue(new Callback<Homestay>() {
            @Override
            public void onResponse(Call<Homestay> call, Response<Homestay> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Homestay homestay = response.body();
                    address = homestay.getWard() + ", " + homestay.getDistrict() + ", " + homestay.getProvince();
                    textAddress.setText(address);
                    textIntro.setText(homestay.getDescription());
                    setRate(homestay.getHomestayId());
                    Log.d("RoomDetailFragment", "Homestay name: " + homestay.getName());
                } else {
                    Toast.makeText(requireContext(), "Không tìm thấy Homestay", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Homestay> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                Log.e("RoomDetailFragment", "API error", t);
            }
        });

        textRoomName.setText(room.getRoomName());
        textAmenities.setText("WiFi miễn phí\nĐưa đón sân bay");
        imageRoom.setImageResource(android.R.drawable.ic_menu_gallery);
        txtTypeRoom.setText("Loại phòng: " + room.getRoomType());
        txtPrice.setText("Giá phòng: " + room.getPrice() + " VNĐ");
    }

    private void setRate(long homestayId) {
        ReviewApi reviewApi = retrofit.create(ReviewApi.class);
        Call<Double> call = reviewApi.getAverageRate(homestayId);
        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()) {
                    double average = response.body();
                    Log.d("RoomDetailFragment", "Average rate: " + average);
                    txtRate.setText(average + "⭐");
                    rate = average;
                } else {
                    Log.e("RoomDetailFragment", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.e("RoomDetailFragment", "API error", t);
            }
        });
    }
}
