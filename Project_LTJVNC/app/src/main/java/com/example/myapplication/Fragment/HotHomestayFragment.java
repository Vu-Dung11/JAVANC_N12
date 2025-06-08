package com.example.myapplication.Fragment;

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

import com.example.myapplication.Adapter.Hot_Homestay_Adapter;
import com.example.myapplication.Api.ReviewApi;
import com.example.myapplication.Model.Homestay;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

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
                    homestayList.addAll(response.body());
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
