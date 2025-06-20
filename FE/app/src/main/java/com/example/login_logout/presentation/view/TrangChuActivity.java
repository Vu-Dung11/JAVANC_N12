package com.example.login_logout.presentation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.login_logout.R;
import com.example.login_logout.databinding.ActivityMainBinding;
import com.example.login_logout.newui.Fragment.HotHomestayFragment;
import com.example.login_logout.newui.Fragment.RoomHistoryFragment;
import com.example.login_logout.presentation.fragment.HomeFragment_lv2;
import com.example.login_logout.presentation.fragment.ProfileFragment;


public class TrangChuActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mở fragment mặc định
        replaceFragment(new HomeFragment_lv2());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home2) {
                replaceFragment(new HomeFragment_lv2());
            } else if (itemId == R.id.love) {
                replaceFragment(new HotHomestayFragment());
            }
              else if (itemId == R.id.booking) {
               replaceFragment(new RoomHistoryFragment());
            }
            else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_1, fragment);
        fragmentTransaction.commit();
    }
}