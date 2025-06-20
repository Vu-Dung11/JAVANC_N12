package com.example.login_logout.presentation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_logout.R;
import com.example.login_logout.data.Service.ApiServices;
import com.example.login_logout.data.api2.HomestayApi;
import com.example.login_logout.data.model2.HomestaySummaryDTO;
import com.example.login_logout.data.model2.SearchHomestayDTO;
import com.example.login_logout.presentation.adapter.HomestayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment_lv2 extends Fragment {

    private RecyclerView recyclerView;
    private HomestayAdapter adapter;
    private HomestayApi homestayApi;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomestayAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Khởi tạo SearchView
        searchView = view.findViewById(R.id.searchView);
        setupSearchView();

        // Khởi tạo HomestayApi
        homestayApi = ApiServices.getInstance().getHomestayApi();

        // Gọi API để lấy danh sách homestay mặc định
        fetchHomestaySummaries();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    fetchHomestaySummaries(); // Load lại danh sách mặc định
                } else {
                    performSearch(newText.trim());
                }
                return true;
            }
        });
    }

    private void fetchHomestaySummaries() {
        Call<List<HomestaySummaryDTO>> call = homestayApi.getHomestaySummaries();
        call.enqueue(new Callback<List<HomestaySummaryDTO>>() {
            @Override
            public void onResponse(Call<List<HomestaySummaryDTO>> call, Response<List<HomestaySummaryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HomestaySummaryDTO> homestayList = response.body();
                    Log.d("HomeFragment", "Fetched homestays: " + homestayList.size());
                    List<HomestayAdapter.HomestayItem> items = new ArrayList<>();
                    int[] imageResources = {R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8, R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12, R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16, R.drawable.home17, R.drawable.home18};
                    for (int i = 0; i < homestayList.size(); i++) {
                        HomestaySummaryDTO dto = homestayList.get(i);
                        dto.setImageResourceId(imageResources[i % imageResources.length]); // Gán ảnh theo thứ tự
                        items.add(new HomestayAdapter.HomestaySummaryItem(dto));
                    }
                    adapter.updateList(items);
                } else {
                    Log.e("HomeFragment", "Failed to fetch homestays: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<HomestaySummaryDTO>> call, Throwable t) {
                Log.e("HomeFragment", "Network error: " + t.getMessage());
            }
        });
    }

    private void performSearch(String keyword) {
        Call<List<SearchHomestayDTO>> call = homestayApi.searchHomestaysByKeyword(keyword);
        call.enqueue(new Callback<List<SearchHomestayDTO>>() {
            @Override
            public void onResponse(Call<List<SearchHomestayDTO>> call, Response<List<SearchHomestayDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SearchHomestayDTO> searchList = response.body();
                    Log.d("HomeFragment", "Searched homestays for keyword '" + keyword + "': " + searchList.size());
                    List<HomestayAdapter.HomestayItem> items = new ArrayList<>();
                    int[] imageResources = {R.drawable.home5, R.drawable.home6, R.drawable.home7, R.drawable.home8, R.drawable.home9, R.drawable.home10, R.drawable.home11, R.drawable.home12, R.drawable.home13, R.drawable.home14, R.drawable.home15, R.drawable.home16, R.drawable.home17, R.drawable.home18}; // Danh sách ảnh
                    for (int i = 0; i < searchList.size(); i++) {
                        SearchHomestayDTO dto = searchList.get(i);
                        dto.setImageResourceId(imageResources[i % imageResources.length]); // Gán ảnh theo thứ tự
                        items.add(new HomestayAdapter.SearchHomestayItem(dto));
                    }
                    adapter.updateList(items);
                } else {
                    Log.e("HomeFragment", "Failed to search homestays: " + response.code() + " - " + response.message());
                    adapter.updateList(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<SearchHomestayDTO>> call, Throwable t) {
                Log.e("HomeFragment", "Network error during search: " + t.getMessage());
                adapter.updateList(new ArrayList<>());
            }
        });
    }
}