package com.example.login_logout.presentation.adapter;

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
import com.example.login_logout.data.model2.HomestaySummaryDTO;
import com.example.login_logout.data.model2.SearchHomestayDTO;
import com.example.login_logout.newui.old_activity.Room_List_Activity;

import java.util.List;

public class HomestayAdapter extends RecyclerView.Adapter<HomestayAdapter.HomestayViewHolder> {

    public interface HomestayItem {
        Long getHomestayId();
        String getName();
        String getAddress();
        Double getAveragePrice();
        String getRoomTypeDescription();
        Double getRateHs();
        int getImageResourceId(); // Thêm phương thức này
    }

    private List<? extends HomestayItem> homestayList;
    private Context context;

    public HomestayAdapter(Context context, List<? extends HomestayItem> homestayList) {
        this.context = context;
        this.homestayList = homestayList;
    }

    @NonNull
    @Override
    public HomestayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_for_location_home_fragment, parent, false);
        return new HomestayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomestayViewHolder holder, int position) {
        HomestayItem homestay = homestayList.get(position);
        holder.textName.setText(homestay.getName());
        holder.textAddress.setText(homestay.getAddress());
        // Chuyển averagePrice sang định dạng tiền tệ (đơn vị VND)
        String price = String.format("%,.0fđ", homestay.getAveragePrice());
        holder.textPrice.setText(price);
        // Hiển thị rating dưới dạng "X.X Sao"
        String rating = String.format("%.1f Sao", homestay.getRateHs());
        holder.textRating.setText(rating);
        // Hiển thị mô tả loại phòng
        holder.textChitietPhong.setText(homestay.getRoomTypeDescription());
        // Gán ảnh từ drawable
        holder.imageHomestay.setImageResource(homestay.getImageResourceId());

        // Thêm sự kiện click để chuyển sang RoomListActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Room_List_Activity.class);
            Log.d("HomestayAdapter", "Clicking homestay with ID: " + homestay.getHomestayId());
            intent.putExtra("homestayId", homestay.getHomestayId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return homestayList != null ? homestayList.size() : 0;
    }

    public static class HomestayViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHomestay;
        TextView textName, textAddress, textPrice, textRating, textChitietPhong;

        public HomestayViewHolder(@NonNull View itemView) {
            super(itemView);
            imageHomestay = itemView.findViewById(R.id.image_homestay); // Thêm này
            textName = itemView.findViewById(R.id.text_name);
            textAddress = itemView.findViewById(R.id.text_address);
            textPrice = itemView.findViewById(R.id.text_price);
            textRating = itemView.findViewById(R.id.text_rating);
            textChitietPhong = itemView.findViewById(R.id.text_chitietphong);
        }
    }

    // Cập nhật danh sách homestay
    public void updateList(List<? extends HomestayItem> newList) {
        this.homestayList = newList;
        notifyDataSetChanged();
    }

    // Triển khai interface cho HomestaySummaryDTO
    public static class HomestaySummaryItem implements HomestayItem {
        private HomestaySummaryDTO summary;

        public HomestaySummaryItem(HomestaySummaryDTO summary) {
            this.summary = summary;
        }

        @Override
        public Long getHomestayId() { return summary.getHomestayId(); }
        @Override
        public String getName() { return summary.getName(); }
        @Override
        public String getAddress() { return summary.getAddress(); }
        @Override
        public Double getAveragePrice() { return summary.getAveragePrice(); }
        @Override
        public String getRoomTypeDescription() { return summary.getRoomTypeDescription(); }
        @Override
        public Double getRateHs() { return summary.getRateHs(); }
        @Override
        public int getImageResourceId() { return summary.getImageResourceId(); } // Thêm này
    }

    // Triển khai interface cho SearchHomestayDTO
    public static class SearchHomestayItem implements HomestayItem {
        private SearchHomestayDTO search;

        public SearchHomestayItem(SearchHomestayDTO search) {
            this.search = search;
        }

        @Override
        public Long getHomestayId() { return search.getHomestayId(); }
        @Override
        public String getName() { return search.getName(); }
        @Override
        public String getAddress() { return search.getAddress(); }
        @Override
        public Double getAveragePrice() { return search.getAveragePrice(); }
        @Override
        public String getRoomTypeDescription() { return search.getRoomTypeDescription(); }
        @Override
        public Double getRateHs() { return search.getRateHs(); }
        @Override
        public int getImageResourceId() { return search.getImageResourceId(); } // Thêm này
    }
}