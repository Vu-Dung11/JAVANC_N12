package com.example.login_logout.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.login_logout.R;
import com.example.login_logout.data.model2.UserDTO;
import com.example.login_logout.utils.PreferencesManager;
import com.example.login_logout.viewmodel.UserViewModel;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText usernameEditText, passwordEditText;
    private Button loginBtn;
    private TextView txtChuyen;
    private ProgressBar progressBar;
    private UserViewModel userViewModel;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "LoginActivity started");

        // Khởi tạo PreferencesManager
        preferencesManager = new PreferencesManager(this);

        // Kiểm tra nếu đã đăng nhập thì chuyển đến TrangChuActivity
        if (preferencesManager.isLoggedIn()) {
            startActivity(new Intent(Login.this, TrangChuActivity.class));
            finish();
            return;
        }

        // Ánh xạ UI
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.edtpassword);
        loginBtn = findViewById(R.id.login_button);
        txtChuyen = findViewById(R.id.login_txt_chuyen);
        progressBar = findViewById(R.id.progressBar);

        // Khởi tạo ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Quan sát dữ liệu
        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Log.d(TAG, "Login successful: " + user.getUserName());
                Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                // Lưu thông tin người dùng bằng PreferencesManager
                preferencesManager.saveUser(
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail()
                );

                // Chuyển sang TrangChuActivity
                startActivity(new Intent(Login.this, TrangChuActivity.class));
                finish();
            }
        });

        userViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Log.e(TAG, "Login error: " + error);
                Toast.makeText(Login.this, error, Toast.LENGTH_LONG).show();
            }
        });

        userViewModel.getIsLoading().observe(this, isLoading -> {
            Log.d(TAG, "Loading state changed: " + isLoading);
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            loginBtn.setEnabled(!isLoading);
        });

        // Chuyển đến màn hình đăng ký
        txtChuyen.setOnClickListener(v -> {
            Log.d(TAG, "Navigating to SignupActivity");
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        });

        // Xử lý đăng nhập
        loginBtn.setOnClickListener(v -> {
            Log.d(TAG, "Login button clicked");
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            Log.d(TAG, "Logging in - Username: " + username);

            if (username.isEmpty() || password.isEmpty()) {
                Log.w(TAG, "Validation failed: Empty fields");
                Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            userViewModel.login(username, password);
        });
    }
}