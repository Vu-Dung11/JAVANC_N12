package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Api.BookingApi;
import com.example.myapplication.Model.Booking;
import com.example.myapplication.Model.Room;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;
import com.example.myapplication.old_activity.RoomHistory_Activity;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {

    private EditText edtCheckIn, edtCheckOut;
    private Button Booking_btn;
    private Room room;
    private String address;
    private Double rate;

    public BookingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        edtCheckIn = view.findViewById(R.id.edtCheckIn);
        edtCheckOut = view.findViewById(R.id.edtCheckOut);
        edtCheckIn.setOnClickListener(v -> showDatePicker(edtCheckIn));
        edtCheckOut.setOnClickListener(v -> showDatePicker(edtCheckOut));

        ImageView imageHomestay = view.findViewById(R.id.imageHomestay);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtAddress = view.findViewById(R.id.txtAddress);
        TextView txtRating = view.findViewById(R.id.txtRating);
        TextView txtGuest = view.findViewById(R.id.txtGuest);
        TextView txtPrice = view.findViewById(R.id.txtPrice);
        Booking_btn = view.findViewById(R.id.btnPay);

        if (getArguments() != null) {
            room = getArguments().getParcelable("room_data");
            address = getArguments().getString("address");
            rate = getArguments().getDouble("rate", 0.0);
        }

        Booking_btn.setOnClickListener(v -> {
            if (room == null) {
                Toast.makeText(requireContext(), "Không có thông tin phòng", Toast.LENGTH_SHORT).show();
                return;
            }

            String checkInDate = edtCheckIn.getText().toString();
            String checkOutDate = edtCheckOut.getText().toString();

            if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng chọn ngày check-in và check-out", Toast.LENGTH_SHORT).show();
                return;
            }

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate1 = LocalDate.parse(checkInDate, inputFormatter);
            LocalDate localDate2 = LocalDate.parse(checkOutDate, inputFormatter);

            LocalDateTime localDateTime1 = localDate1.atStartOfDay();
            LocalDateTime localDateTime2 = localDate2.atStartOfDay();

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String checkInStr = localDateTime1.format(outputFormatter);
            String checkOutStr = localDateTime2.format(outputFormatter);

            Booking booking = new Booking();
            booking.setCheckInDate(checkInStr);
            booking.setCheckOutDate(checkOutStr);
            booking.setDepositPrice(500000.0);
            booking.setStatus(1);

            long daysBetween = ChronoUnit.DAYS.between(localDate1, localDate2);
            double totalPrice = (daysBetween <= 0) ? room.getPrice() : daysBetween * room.getPrice();
            booking.setTotalPrice(totalPrice);

            User user = new User();
            user.setUserId(1L);
            booking.setUser(user);

            List<Room> roomList = new ArrayList<>();
            Room newRoom = new Room();
            newRoom.setRoomId(room.getRoomId());
            roomList.add(newRoom);
            booking.setListRoom(roomList);

            BookingApi bookingApi = RetrofitClientInstance.getRetrofitInstance().create(BookingApi.class);
            Call<Booking> call = bookingApi.createBooking(booking);

            call.enqueue(new Callback<Booking>() {
                @Override
                public void onResponse(Call<Booking> call, Response<Booking> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Đặt phòng thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(requireContext(), RoomHistory_Activity.class));
                    } else {
                        try {
                            String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Không có thông báo lỗi";
                            Log.e("API_ERROR", "Lỗi đặt phòng - Mã: " + response.code() + ", Chi tiết: " + errorMsg);
                        } catch (Exception e) {
                            Log.e("API_ERROR", "Lỗi khi đọc errorBody", e);
                        }
                        Toast.makeText(requireContext(), "Lỗi đặt phòng! Mã: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Booking> call, Throwable t) {
                    Log.e("RetrofitError", "Lỗi: " + t.getMessage(), t);
                    Toast.makeText(requireContext(), "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        if (room != null) {
            txtTitle.setText(room.getRoomName());
            txtPrice.setText(String.format("%,.0f VNĐ", room.getPrice()));
            txtAddress.setText(address);
            txtRating.setText(rate + "⭐");
            txtGuest.setText("Loại phòng: " + room.getRoomType());
            imageHomestay.setImageResource(R.drawable.image_1);
        }

        return view;
    }

    private void showDatePicker(EditText targetEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    targetEditText.setText(date);

                    if (room != null) {
                        TextView txtPrice = getView().findViewById(R.id.txtPrice);
                        updateTotalPrice(room, txtPrice);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void updateTotalPrice(Room room, TextView txtPrice) {
        String checkInDate = edtCheckIn.getText().toString();
        String checkOutDate = edtCheckOut.getText().toString();

        if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
            return;
        }

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateIn = LocalDate.parse(checkInDate, inputFormatter);
            LocalDate dateOut = LocalDate.parse(checkOutDate, inputFormatter);

            long daysBetween = ChronoUnit.DAYS.between(dateIn, dateOut);

            if (daysBetween <= 0) {
                txtPrice.setText(String.format("%,.0f VNĐ", room.getPrice()));
            } else {
                double totalPrice = daysBetween * room.getPrice();
                txtPrice.setText(String.format("%,.0f VNĐ", totalPrice));
            }
        } catch (Exception e) {
            txtPrice.setText(String.format("%,.0f VNĐ", room.getPrice()));
            e.printStackTrace();
        }
    }
}
