package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.BookingBill;
import com.example.myapplication.R;

import java.util.List;

public class Room_History_Adapter extends RecyclerView.Adapter<Room_History_Adapter.RoomViewHolder> {

    private List<BookingBill> bookingBillList;

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
        holder.txtAddress.setText("Mã phòng: " + booking.getRoomId());
        holder.txtStatus.setText("Ngày nhận phòng: " + booking.getCheckInDate());
        holder.txtTotal.setText("Tổng tiền: " + booking.getTotalPrice() + " VNĐ");
        holder.txtDeposit.setText("Tiền cọc: " + booking.getDepositPrice() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return bookingBillList.size();
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
}
