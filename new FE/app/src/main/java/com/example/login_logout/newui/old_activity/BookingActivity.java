package com.example.login_logout.newui.old_activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_logout.R;
import com.example.login_logout.newui.Api.BookingApi;
import com.example.login_logout.newui.Model.Booking;
import com.example.login_logout.newui.Model.Room;
import com.example.login_logout.newui.Model.User;
import com.example.login_logout.newui.retrofit.RetrofitClientInstance;
import com.example.login_logout.presentation.view.TrangChuActivity;
import com.example.login_logout.utils.PreferencesManager;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    EditText edtCheckIn, edtCheckOut;
    Button Booking_btn;
    ImageView btnBack;

    private PreferencesManager preferencesManager;

    // Mảng chứa các ID tài nguyên ảnh từ home1 đến home18
    private final int[] homeImages = {
            R.drawable.home1, R.drawable.home2, R.drawable.home3, R.drawable.home4,
            R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8,
            R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12,
            R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16,
            R.drawable.home17, R.drawable.home18
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_booking);

        // Khởi tạo PreferencesManager với context của activity
        preferencesManager = new PreferencesManager(this);

        // Lấy dữ liệu từ intent
        Room room = getIntent().getParcelableExtra("room_data");
        String address = getIntent().getStringExtra("address");
        Double rate = getIntent().getDoubleExtra("rate", 0.0);
        // Khởi tạo các view
        edtCheckIn = findViewById(R.id.edtCheckIn);
        edtCheckOut = findViewById(R.id.edtCheckOut);
        edtCheckIn.setOnClickListener(v -> showDatePicker(edtCheckIn));
        edtCheckOut.setOnClickListener(v -> showDatePicker(edtCheckOut));
        // Khởi tạo các view
        ImageView imageHomestay = findViewById(R.id.imageHomestay);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtAddress = findViewById(R.id.txtAddress);
        TextView txtRating = findViewById(R.id.txtRating);
        TextView txtGuest = findViewById(R.id.txtGuest);
        TextView txtPrice = findViewById(R.id.txtPrice);
        Booking_btn = findViewById(R.id.btnPay);
        btnBack = findViewById(R.id.btnBack);
        // Xử lý nút thanh toán
        Booking_btn.setOnClickListener(v -> {
            if (room == null) {
                Toast.makeText(this, "Không có thông tin phòng", Toast.LENGTH_SHORT).show();
                return;
            }
            String checkInDate = edtCheckIn.getText().toString();     // "05/06/2025"
            String checkOutDate = edtCheckOut.getText().toString();   // "07/06/2025"

            if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày check-in và check-out", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
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

                Booking booking = new Booking();
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
                booking.setDepositPrice(50.0);

                booking.setStatus(1);
                long daysBetween = ChronoUnit.DAYS.between(localDate1, localDate2);
                double totalPrice = (daysBetween <= 0) ? room.getPrice() : daysBetween * room.getPrice();
                booking.setTotalPrice(totalPrice);

                // ✅ Tạo User object đúng format
                User user = new User();

                Long userId = preferencesManager.getUserId();
                user.setUserId(userId);
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
                            Toast.makeText(BookingActivity.this, "Đặt phòng thành công! cảm ơn quý khách đã đặt phòng!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BookingActivity.this, TrangChuActivity.class);
                            startActivity(intent);
                        } else {
                            try {
                                String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Không có thông báo lỗi";
                                Log.e("API_ERROR", "Lỗi đặt phòng - Mã: " + response.code() + ", Chi tiết: " + errorMsg);
                            } catch (Exception e) {
                                Log.e("API_ERROR", "Lỗi khi đọc errorBody", e);
                            }
                            Toast.makeText(BookingActivity.this, "Lỗi đặt phòng! Mã: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Booking> call, Throwable t) {
                        Log.e("RetrofitError", "Lỗi: " + t.getMessage(), t);
                        Toast.makeText(BookingActivity.this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e("BookingError", "Lỗi khi xử lý ngày", e);
                Toast.makeText(this, "Lỗi khi xử lý ngày!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút back
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại và quay lại màn hình trước
        });

        if (room != null) {
            txtTitle.setText(room.getRoomName());
            txtPrice.setText(String.format("%,.0f VNĐ", room.getPrice()));
            txtAddress.setText(address);
            txtRating.setText(rate + "⭐");
            txtGuest.setText("Loại phòng: " + room.getRoomType());
            // Hiển thị ảnh ngẫu nhiên từ home1 đến home18
            int randomImageIndex = getRandomImageIndex();
            imageHomestay.setImageResource(homeImages[randomImageIndex]);
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

    // Hàm tạo chỉ số ngẫu nhiên từ 0 đến 17 (tương ứng với 18 ảnh)
    private int getRandomImageIndex() {
        Random random = new Random();
        return random.nextInt(homeImages.length); // Tạo số từ 0 đến 17
    }
}