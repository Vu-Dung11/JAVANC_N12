package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gắn Fragment nếu là lần đầu vào Activity
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment hothomestay_fragment = new com.example.myapplication.Fragment.HotHomestayFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, hothomestay_fragment)
                    .commit();
        }
    }
}
