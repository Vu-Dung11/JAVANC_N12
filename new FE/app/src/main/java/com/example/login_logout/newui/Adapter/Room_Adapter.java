package com.example.login_logout.newui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_logout.R;
import com.example.login_logout.newui.Model.Room;
import com.example.login_logout.newui.old_activity.RoomDetailActivity;

import java.util.List;
import java.util.Random;

public class Room_Adapter extends RecyclerView.Adapter<Room_Adapter.RoomViewHolder> {
    private List<Room> roomList;
    private Context context;

    // Mảng chứa các ID tài nguyên ảnh từ home1 đến home18
    private final int[] homeImages = {
            R.drawable.home1, R.drawable.home2, R.drawable.home3, R.drawable.home4,
            R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8,
            R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12,
            R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16,
            R.drawable.home17, R.drawable.home18
    };

    public Room_Adapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.txtRoomId.setText("Thông tin phòng");
        holder.txtRoomName.setText(room.getRoomName());
        holder.txtRoomPrice.setText("Giá: " + room.getPrice() + "USD");

        if (room.getRoomType() == 1) {
            holder.txtTypeRoom.setText("Loại phòng: Phòng đơn");
        } else if (room.getRoomType() == 2) {
            holder.txtTypeRoom.setText("Loại phòng: Phòng đôi");
        } else {
            holder.txtTypeRoom.setText("Loại phòng: " + room.getRoomType());
        }

        // Hiển thị ảnh ngẫu nhiên từ home1 đến home18
        int randomImageIndex = getRandomImageIndex();
        holder.imgRoom.setImageResource(homeImages[randomImageIndex]);

        // Sự kiện click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetailActivity.class);
            intent.putExtra("room", room);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return roomList != null ? roomList.size() : 0;
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView txtRoomName, txtRoomPrice, txtRoomId, txtTypeRoom;
        ImageView imgRoom;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRoomName = itemView.findViewById(R.id.txtRoomName_Item);
            txtRoomPrice = itemView.findViewById(R.id.txtRoomPrice_Item);
            imgRoom = itemView.findViewById(R.id.imgRoom_Item);
            txtRoomId = itemView.findViewById(R.id.txtRoomId_Item);
            txtTypeRoom = itemView.findViewById(R.id.txtRoomType_Item);
        }
    }

    // Hàm tạo chỉ số ngẫu nhiên từ 0 đến 17 (tương ứng với 18 ảnh)
    private int getRandomImageIndex() {
        Random random = new Random();
        return random.nextInt(homeImages.length); // Tạo số từ 0 đến 17
    }
}