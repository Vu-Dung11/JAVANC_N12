package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Model.Room;
import com.example.myapplication.old_activity.RoomDetailActivity;

import java.util.List;

public class Room_Adapter extends RecyclerView.Adapter<Room_Adapter.RoomViewHolder> {
    private List<Room> roomList;
    private Context context;

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
        holder.txtRoomId.setText("Mã phòng:"+room.getRoomId());
        holder.txtRoomName.setText(room.getRoomName());
        holder.txtRoomPrice.setText("Giá: " + room.getPrice() + " VND");
        holder.txtTypeRoom.setText("Loại phòng: "+room.getRoomType());

        // Load ảnh (dùng Glide/Picasso)
//        Glide.with(context).load(room.getImageUrl()).into(holder.imgRoom);

        // Sự kiện click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetailActivity.class);
            intent.putExtra("room", room);
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView txtRoomName, txtRoomPrice, txtRoomId,txtTypeRoom;
        ImageView imgRoom;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRoomName = itemView.findViewById(R.id.txtRoomName_Item);
            txtRoomPrice = itemView.findViewById(R.id.txtRoomPrice_Item);
            imgRoom = itemView.findViewById(R.id.imgRoom_Item);
            txtRoomId = itemView.findViewById(R.id.txtRoomId_Item);
            txtTypeRoom= itemView.findViewById(R.id.txtRoomType_Item);
        }
    }
}
