package com.example.login_logout.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.login_logout.R;
import com.example.login_logout.presentation.view.Login;
import com.example.login_logout.utils.PreferencesManager;

public class ProfileFragment extends Fragment {
    private TextView tvUserName, tvUserEmail, tvUserBadge;
    private Button logoutBTN;
    private PreferencesManager preferencesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        
        // Khởi tạo PreferencesManager
        preferencesManager = new PreferencesManager(requireContext());
        
        // Ánh xạ view
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserBadge = view.findViewById(R.id.tvUserBadge);
        logoutBTN = view.findViewById(R.id.logoutBTN);
        
        // Hiển thị thông tin người dùng
        displayUserInfo();
        
        // Xử lý sự kiện đăng xuất
        logoutBTN.setOnClickListener(v -> handleLogout());
        
        return view;
    }
    
    private void displayUserInfo() {
        String userName = preferencesManager.getUserName();
        String email = preferencesManager.getEmail();
        
        if (userName != null && !userName.isEmpty()) {
            tvUserName.setText(userName);
        }
        
        if (email != null && !email.isEmpty()) {
            tvUserEmail.setText(email);
        }
        
        // Hiển thị huy hiệu thành viên (có thể thay đổi logic này tùy theo yêu cầu)
        tvUserBadge.setText("Người dùng");
    }
    
    private void handleLogout() {
        // Xóa thông tin người dùng
        preferencesManager.clearUser();
        
        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
        
        Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
    }
}