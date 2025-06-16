package com.example.login_logout.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu thông tin người dùng (không có token)
    public void saveUser(Long userId, String userName, String email) {
        editor.putLong(KEY_USER_ID, userId != null ? userId : -1);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Xóa thông tin khi đăng xuất
    public void clearUser() {
        editor.clear();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Lấy thông tin người dùng
    public Long getUserId() {
        long userId = sharedPreferences.getLong(KEY_USER_ID, -1);
        return userId != -1 ? userId : null;
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    // Loại bỏ getToken() vì không cần token
}