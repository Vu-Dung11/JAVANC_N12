package com.example.myapplication.old_activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import retrofit2.Callback;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;


import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Api.BookingApi;
import android.util.Log;




import java.util.Calendar;

import com.example.myapplication.Model.Booking;
import com.example.myapplication.Model.Room;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    EditText edtCheckIn, edtCheckOut;
    Button Booking_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_booking); // đổi thành tên layout XML của bạn

        Room room = getIntent().getParcelableExtra("room_data");
        String address = getIntent().getStringExtra("address");
        Double rate = getIntent().getDoubleExtra("rate",0.0);

        edtCheckIn = findViewById(R.id.edtCheckIn);
        edtCheckOut = findViewById(R.id.edtCheckOut);
        edtCheckIn.setOnClickListener(v -> showDatePicker(edtCheckIn));
        edtCheckOut.setOnClickListener(v -> showDatePicker(edtCheckOut));

        ImageView imageHomestay = findViewById(R.id.imageHomestay);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtAddress = findViewById(R.id.txtAddress);
        TextView txtRating = findViewById(R.id.txtRating);
        TextView txtGuest = findViewById(R.id.txtGuest);
        TextView txtPrice = findViewById(R.id.txtPrice);
        Booking_btn= findViewById(R.id.btnPay);
        // Xử lý nút thanh toán
        Booking_btn.setOnClickListener(v -> {
            if (room == null) {
                Toast.makeText(this, "Không có thông tin phòng", Toast.LENGTH_SHORT).show();
                return;
            }
            String checkInDate = edtCheckIn.getText().toString();     // "05/06/2025"
            String checkOutDate = edtCheckOut.getText().toString();   // "07/06/2025"

// 1. Parse chuỗi "05/06/2025" thành LocalDate
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate1 = LocalDate.parse(checkInDate, inputFormatter);
            LocalDate localDate2 = LocalDate.parse(checkOutDate, inputFormatter);

// 2. Tạo LocalDateTime với giờ mặc định là 00:00
            LocalDateTime localDateTime1 = localDate1.atStartOfDay();
            LocalDateTime localDateTime2 = localDate2.atStartOfDay();

// 3. Chuyển về chuỗi định dạng ISO 8601
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String checkInStr = localDateTime1.format(outputFormatter);
            String checkOutStr = localDateTime2.format(outputFormatter);

            if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày check-in và check-out", Toast.LENGTH_SHORT).show();
                return;
            }

            Booking booking = new Booking();

//            String checkInStr = "2024-10-01T00:00:00";
////            booking.setCheck_in_date(checkInDate1);
//            String checkOut = "2024-10-01T00:00:00";
////            booking.setCheck_out_date(checkOutDate1);

            booking.setCheckInDate(checkInStr);
            booking.setCheckOutDate(checkOutStr);
            // Kiểm tra và in ra Logcat
            if (booking.getCheckInDate() == null) {
                Log.d("BookingCheck", "Check-in date is null");
            } else {
                Log.d("BookingCheck", "Check-in date is: " + booking.getCheckInDate().toString());
            }

            if (booking.getCheckOutDate() == null) {
                Log.d("BookingCheck", "Check-out date is null");
            } else {
                Log.d("BookingCheck", "Check-out date is: " + booking.getCheckOutDate().toString());
            }
            booking.setDepositPrice(500000.0);
//            booking.setTotalPrice(room.getPrice());
            booking.setStatus(1);
            long daysBetween = ChronoUnit.DAYS.between(localDate1, localDate2);
            double totalPrice = (daysBetween <= 0) ? room.getPrice() : daysBetween * room.getPrice();
            booking.setTotalPrice(totalPrice);

            // ✅ Tạo User object đúng format
            User user = new User();

            user.setUserId(1L);//Gán Id_User sau khi dđuoươợc truyền vào đây
            booking.setUser(user);
            // Lưu room
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
                        Toast.makeText(BookingActivity.this, "Đặt phòng thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookingActivity.this, RoomHistory_Activity.class);
                        startActivity(intent);
                    } else {
                        try {
                            String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Không có thông báo lỗi";
                            Log.e("API_ERROR", "Lỗi đặt phòng - Mã: " + response.code() + ", Chi tiết: " + errorMsg);
                        } catch (Exception e) {
                            Log.e("API_ERROR", "Lỗi khi đọc errorBody", e);
                        }
                        Toast.makeText(BookingActivity.this, "Lỗi đặt phòng! Mã: " + response.code(), Toast.LENGTH_SHORT).show();                    }
                }

                @Override
                public void onFailure(Call<Booking> call, Throwable t) {
                    Log.e("RetrofitError", "Lỗi: " + t.getMessage(), t);
                    Toast.makeText(BookingActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        if (room != null) {
            txtTitle.setText(room.getRoomName());
            txtPrice.setText(String.format("%,.0f VNĐ", room.getPrice()));
            txtAddress.setText(address);
            txtRating.setText(rate + "⭐");
            txtGuest.setText("Loại phòng: "+room.getRoomType());
            imageHomestay.setImageResource(R.drawable.image_1); // hoặc dùng Glide để load ảnh động
        }
    }




    private void showDatePicker(EditText targetEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    targetEditText.setText(date);

                    // ✅ Cập nhật tổng giá ngay khi chọn ngày
                    Room room = getIntent().getParcelableExtra("room_data");
                    TextView txtPrice = findViewById(R.id.txtPrice);
                    updateTotalPrice(room, txtPrice);
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
                txtPrice.setText(String.format("%,.0f VNĐ", room.getPrice())); // hiển thị giá gốc
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
