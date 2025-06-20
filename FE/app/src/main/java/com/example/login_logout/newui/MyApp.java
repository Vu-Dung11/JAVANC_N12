package com.example.login_logout.newui;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this); // Khởi tạo thư viện
    }
}
