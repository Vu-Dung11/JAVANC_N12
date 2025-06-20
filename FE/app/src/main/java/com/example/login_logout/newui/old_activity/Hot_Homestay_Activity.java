package com.example.login_logout.newui.old_activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_logout.R;
import com.example.login_logout.newui.Adapter.Hot_Homestay_Adapter;
import com.example.login_logout.newui.Api.ReviewApi;
import com.example.login_logout.newui.Model.Homestay;
import com.example.login_logout.newui.retrofit.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Hot_Homestay_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Hot_Homestay_Adapter adapter;
    private List<Homestay> homestayList;
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hot_homestay);

        recyclerView = findViewById(R.id.recyclerHotHomestay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homestayList = new ArrayList<>();

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<List<Homestay>> call = reviewApi.getHomestayByHighRate();

        call.enqueue(new Callback<List<Homestay>>() {
            @Override
            public void onResponse(Call<List<Homestay>> call, Response<List<Homestay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Homestay> homestays = response.body();
                    Log.d("API_RESPONSE", "Số homestay nhận được: " + homestays.size());

                    for (int i = 0; i < homestays.size(); i++) {
                        Homestay source = homestays.get(i);  // homestay từ API
                        Homestay homestay = new Homestay();  // tạo bản sao (nếu cần chỉnh sửa)
                        homestay.setHomestayId(source.getHomestayId());
                        homestay.setName(source.getName());
                        homestay.setWard(source.getWard());
                        homestay.setDistrict(source.getDistrict());
                        homestay.setProvince(source.getProvince());
                        homestayList.add(homestay);  // thêm vào danh sách hiển thị
}
                    // TODO: Gắn danh sách này vào adapter RecyclerView
                    // Ví dụ:
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API_ERROR", "Lỗi phản hồi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Homestay>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi gọi API", t);
            }
        });



        adapter = new Hot_Homestay_Adapter(this, homestayList);
        recyclerView.setAdapter(adapter);


    }
}
