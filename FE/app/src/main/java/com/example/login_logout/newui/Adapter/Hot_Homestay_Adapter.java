package com.example.login_logout.newui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_logout.R;
import com.example.login_logout.newui.Model.Homestay;
import com.example.login_logout.newui.old_activity.Room_List_Activity;

import java.util.ArrayList;
import java.util.List;

public class Hot_Homestay_Adapter extends RecyclerView.Adapter<Hot_Homestay_Adapter.HomestayViewHolder> {

    private final Context context;
    private List<Homestay> homestayList;

    public Hot_Homestay_Adapter(Context context, List<Homestay> homestayList) {
        this.context = context;
        this.homestayList = homestayList != null ? homestayList : new ArrayList<>();
    }

    @NonNull
    @Override
    public HomestayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_homestay_item, parent, false);
        return new HomestayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomestayViewHolder holder, int position) {
        Homestay homestay = homestayList.get(position);
        if (homestay != null) {
            Log.d("Hot_Homestay_Adapter", "Binding homestay: " + homestay.getName() + ", ID: " + homestay.getHomestayId());

            // Hiển thị tên homestay
            holder.txtRoomName.setText(homestay.getName());

            // Hiển thị địa chỉ
            String address = (homestay.getWard() != null ? homestay.getWard() + ", " : "") +
                    (homestay.getDistrict() != null ? homestay.getDistrict() + ", " : "") +
                    (homestay.getProvince() != null ? homestay.getProvince() : "");
            holder.txtAddress.setText(address.trim());

            // Hiển thị rating từ Homestay (nếu có)
            Double rate = homestay.getRate(); // Giả định Homestay có phương thức getRate()
            holder.txtRating.setText("⭐ Hiện đang rất");

            // Hiển thị nhãn "Hot"
            holder.txtHot.setText("Hot");

            // Hiển thị ảnh từ drawable
            holder.imageHomestay.setImageResource(homestay.getImageResourceId());

            // Xử lý sự kiện click
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, Room_List_Activity.class);
                intent.putExtra("homestayId", homestay.getHomestayId());
                Log.d("Hot_Homestay_Adapter", "Clicking homestay with ID: " + homestay.getHomestayId());
                context.startActivity(intent);

                // Áp dụng hiệu ứng chuyển trang nếu cần
                // ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
    }

    @Override
    public int getItemCount() {
        return homestayList != null ? homestayList.size() : 0;
    }

    public static class HomestayViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHomestay;
        TextView txtRoomName, txtAddress, txtRating, txtHot;

        public HomestayViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHomestay = itemView.findViewById(R.id.imageHomestay);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtHot = itemView.findViewById(R.id.txtHot); // Thêm tham chiếu đến txtHot
        }
    }
}