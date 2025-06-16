package com.example.login_logout.newui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_logout.R;
import com.example.login_logout.newui.Model.BookingBill;

import java.util.List;
import java.util.Random;

public class Room_History_Adapter extends RecyclerView.Adapter<Room_History_Adapter.RoomViewHolder> {

    private List<BookingBill> bookingBillList;

    // Mảng chứa các ID tài nguyên ảnh từ home1 đến home18
    private final int[] homeImages = {
            R.drawable.home1, R.drawable.home2, R.drawable.home3, R.drawable.home4,
            R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8,
            R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12,
            R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16,
            R.drawable.home17, R.drawable.home18
    };

    public Room_History_Adapter(List<BookingBill> bookingBillList) {
        this.bookingBillList = bookingBillList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_room, parent, false); // Đảm bảo bạn có file XML này
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        BookingBill booking = bookingBillList.get(position);

        holder.txtTitle.setText(booking.getRoomName());
        holder.txtAddress.setText("Địa chỉ: " + booking.getAddress());
        holder.txtStatus.setText("Ngày nhận phòng: " + booking.getCheckInDate());
        holder.txtTotal.setText("Tổng tiền: " + booking.getTotalPrice() + " USD");
        holder.txtDeposit.setText("Tiền cọc: " + booking.getDepositPrice() + " USD");

        // Hiển thị ảnh ngẫu nhiên từ home1 đến home18
        int randomImageIndex = getRandomImageIndex();
        holder.imgHomestay.setImageResource(homeImages[randomImageIndex]);
    }

    @Override
    public int getItemCount() {
        return bookingBillList != null ? bookingBillList.size() : 0;
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHomestay;
        TextView txtTitle, txtAddress, txtStatus, txtTotal, txtDeposit;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHomestay = itemView.findViewById(R.id.imgHomestay);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDeposit = itemView.findViewById(R.id.txtDeposit);
        }
    }

    // Hàm tạo chỉ số ngẫu nhiên từ 0 đến 17 (tương ứng với 18 ảnh)
    private int getRandomImageIndex() {
        Random random = new Random();
        return random.nextInt(homeImages.length); // Tạo số từ 0 đến 17
    }
}