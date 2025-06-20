package com.example.login_logout.newui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class HotHomestayFragment extends Fragment {

    private RecyclerView recyclerView;
    private Hot_Homestay_Adapter adapter;
    private final List<Homestay> homestayList = new ArrayList<>();
    private Retrofit retrofit;

    public HotHomestayFragment() {
        // Bắt buộc phải có constructor rỗng
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_homestay, container, false);

        recyclerView = view.findViewById(R.id.recyclerHotHomestay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Hot_Homestay_Adapter(getContext(), homestayList);
        recyclerView.setAdapter(adapter);

        retrofit = RetrofitClientInstance.getRetrofitInstance();

        fetchHotHomestays();

        return view;
    }

    private void fetchHotHomestays() {
        ReviewApi reviewApi = retrofit.create(ReviewApi.class);
        Call<List<Homestay>> call = reviewApi.getHomestayByHighRate();

        call.enqueue(new Callback<List<Homestay>>() {
            @Override
            public void onResponse(Call<List<Homestay>> call, Response<List<Homestay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homestayList.clear();
                    int[] imageResources = {R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8, R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12, R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16, R.drawable.home17, R.drawable.home18};
                    for (int i = 0; i < response.body().size(); i++) {
                        Homestay homestay = response.body().get(i);
                        homestay.setImageResourceId(imageResources[i % imageResources.length]); // Gán ảnh tĩnh
                        homestayList.add(homestay);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API_ERROR", "Lỗi phản hồi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Homestay>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi kết nối API", t);
            }
        });
    }
}