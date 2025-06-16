package com.example.login_logout.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.login_logout.R;
import com.example.login_logout.data.model2.UserDTO;
import com.example.login_logout.utils.PreferencesManager;
import com.example.login_logout.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private TextInputEditText edtUserName, edtFullName, edtPhoneNumb, edtEmail, edtPassword;
    private Spinner spinnerGender;
    private Button btnSignUp;
    private TextView tvLogin;
    private ProgressBar progressBar;
    private UserViewModel userViewModel;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Log.d(TAG, "SignupActivity started");

        // Khởi tạo PreferencesManager
        preferencesManager = new PreferencesManager(this);

        // Ánh xạ view
        edtUserName = findViewById(R.id.edtUserName);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhoneNumb = findViewById(R.id.edtPhoneNumb);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSignUp = findViewById(R.id.button);
        tvLogin = findViewById(R.id.sign_up_chuyen);
        progressBar = findViewById(R.id.progressBar);

        // Khởi tạo ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Quan sát dữ liệu
        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Log.d(TAG, "Registration successful: " + user.getUserName());
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                
                // Lưu thông tin người dùng bằng PreferencesManager
                preferencesManager.saveUser(
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail()
                );
                
                startActivity(new Intent(Signup.this, Login.class));
                finish();
            }
        });

        userViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Log.e(TAG, "Registration error: " + error);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        userViewModel.getIsLoading().observe(this, isLoading -> {
            Log.d(TAG, "Loading state changed: " + isLoading);
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnSignUp.setEnabled(!isLoading);
        });

        // Xử lý sự kiện đăng ký
        btnSignUp.setOnClickListener(v -> {
            Log.d(TAG, "Sign up button clicked");
            registerUser();
        });

        // Chuyển đến màn hình đăng nhập
        tvLogin.setOnClickListener(v -> {
            Log.d(TAG, "Navigating to LoginActivity");
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });
    }

    private void registerUser() {
        String username = edtUserName.getText().toString().trim();
        String fullName = edtFullName.getText().toString().trim();
        String phone = edtPhoneNumb.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        int gender = spinnerGender.getSelectedItemPosition();

        Log.d(TAG, "Registering user - Username: " + username + ", Email: " + email);

        if (username.isEmpty() || fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Log.w(TAG, "Validation failed: Empty fields");
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(username);
        userDTO.setFullName(fullName);
        userDTO.setPhoneNumber(phone);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setGender(gender);

        userViewModel.register(userDTO);
    }
}