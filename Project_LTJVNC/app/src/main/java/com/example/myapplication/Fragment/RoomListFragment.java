package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Room_Adapter;
import com.example.myapplication.Api.RoomApi;
import com.example.myapplication.Model.Room;
import com.example.myapplication.R;
import com.example.myapplication.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListFragment extends Fragment {

    private RecyclerView recyclerRoom;
    private Room_Adapter adapter;

    // Khai báo key truyền dữ liệu homestayId
    private static final String ARG_HOMESTAY_ID = "homestayId";
    private long homestayId;

    // Phương thức tạo instance Fragment với homestayId truyền vào
    public static RoomListFragment newInstance(long homestayId) {
        RoomListFragment fragment = new RoomListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_HOMESTAY_ID, homestayId);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_room, container, false);

        recyclerRoom = view.findViewById(R.id.recyclerRoomList);
        recyclerRoom.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            homestayId = getArguments().getLong(ARG_HOMESTAY_ID, -1L);
        }

        if (homestayId == -1L) {
            Toast.makeText(requireContext(), "Thiếu Homestay ID", Toast.LENGTH_SHORT).show();
            return view;
        }

        fetchRoomsByHomestayId(homestayId);

        return view;
    }

    private void fetchRoomsByHomestayId(long homestayId) {
        RoomApi roomApi = RetrofitClientInstance.getRetrofitInstance().create(RoomApi.class);
        Call<List<Room>> call = roomApi.getRoomsByHomestayId(homestayId);

        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Room> roomList = response.body();
                    adapter = new Room_Adapter(requireContext(), roomList);
                    recyclerRoom.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Không lấy được danh sách phòng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                Log.e("RoomListFragment", "Lỗi kết nối Server", t);
            }
        });
    }
}
