package com.example.myapplication.Adapter;
import android.util.Log;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Homestay;
import com.example.myapplication.R;
import com.example.myapplication.old_activity.Room_List_Activity;

import java.util.List;

public class Hot_Homestay_Adapter extends RecyclerView.Adapter<Hot_Homestay_Adapter.HomestayViewHolder> {

    private Context context;
    private List<Homestay> homestayList;

    public Hot_Homestay_Adapter(Context context, List<Homestay> homestayList) {
        this.context = context;
        this.homestayList = homestayList;
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
        Log.d("HomestayAdapter", "homestay: " + homestay);
        holder.txtRoomName.setText(homestay.getName());

        String address = homestay.getWard() + ", " + homestay.getDistrict() + ", " + homestay.getProvince();
        holder.txtAddress.setText(address);
        holder.txtRating.setText("⭐ 5 / 5");
        //Chuyển trang sang trang danh sách phòng khi click vào 1 Home
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Room_List_Activity.class);
            Log.d("HomestayAdapter", "Homestay at position " + position + ": " + new Gson().toJson(homestay));
            Log.d("HomestayAdapter", "homestayId: " + homestay.getHomestayId());
            intent.putExtra("homestayId", homestay.getHomestayId()); // truyền dữ liệu nếu cần
            context.startActivity(intent);

            // Áp dụng hiệu ứng chuyển trang
//            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    @Override
    public int getItemCount() {
        return homestayList.size();
    }

    public static class HomestayViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHomestay;
        TextView txtRoomName, txtAddress, txtRating;

        public HomestayViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHomestay = itemView.findViewById(R.id.imageHomestay);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtRating = itemView.findViewById(R.id.txtRating);
        }
    }
}
