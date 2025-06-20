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
import com.example.login_logout.newui.Adapter.Room_History_Adapter;
import com.example.login_logout.newui.Api.BookingApi;
import com.example.login_logout.newui.Model.BookingBill;
import com.example.login_logout.newui.retrofit.RetrofitClientInstance;
import com.example.login_logout.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RoomHistoryFragment extends Fragment {

    private RecyclerView recyclerViewRooms;
    private Room_History_Adapter roomAdapter;
    private List<BookingBill> bookingBills = new ArrayList<>();

    private BookingApi bookingApi;

    private static final String TAG = "RoomHistoryFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_room_history, container, false);

        recyclerViewRooms = rootView.findViewById(R.id.recyclerViewRooms);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(requireContext()));
        roomAdapter = new Room_History_Adapter(bookingBills);
        recyclerViewRooms.setAdapter(roomAdapter);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        bookingApi = retrofit.create(BookingApi.class);

        PreferencesManager preferencesManager = new PreferencesManager(requireContext());
        Long userId = preferencesManager.getUserId();

        if (userId != null) {
            fetchBookingHistory(userId);
        } else {
            Log.e(TAG, "UserId is null, cannot fetch booking history");
        }

        return rootView;
    }

    private void fetchBookingHistory(Long userId) {
        Call<List<BookingBill>> call = bookingApi.getBookingHistoryByUserId(userId);
        call.enqueue(new Callback<List<BookingBill>>() {
            @Override
            public void onResponse(Call<List<BookingBill>> call, Response<List<BookingBill>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookingBill> fetchedBills = response.body();
                    Log.d(TAG, "Fetched booking history: " + fetchedBills.size());

                    // Cập nhật danh sách bookingBills
                    bookingBills.clear();
                    bookingBills.addAll(fetchedBills);

                    roomAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Failed to fetch booking history: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<BookingBill>> call, Throwable t) {
                Log.e(TAG, "Network error fetching booking history: " + t.getMessage());
            }
        });
    }
}