package com.example.login_logout.newui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.login_logout.newui.Fragment.HotHomestayFragment;
import com.example.login_logout.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Gắn Fragment nếu là lần đầu vào Activity
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment hothomestay_fragment = new HotHomestayFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, hothomestay_fragment)
                    .commit();
        }
    }
}
