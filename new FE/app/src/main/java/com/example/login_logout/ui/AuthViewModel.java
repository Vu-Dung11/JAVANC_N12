package com.example.login_logout.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.login_logout.data.Service.ApiServices;
import com.example.login_logout.data.api2.UserApi;
import com.example.login_logout.data.model2.UserDTO;
import com.example.login_logout.data.model2.LoginResponse;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {
    private MutableLiveData<UserDTO> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> loginMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final UserApi userApi;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        userApi = ApiServices.getInstance().getUserApi();
    }

    public LiveData<UserDTO> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getLoginMessageLiveData() {
        return loginMessageLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void register(UserDTO userDTO) {
        isLoading.setValue(true);
        Call<UserDTO> call = userApi.register(userDTO);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    userLiveData.setValue(response.body());
                } else {
                    errorMessage.setValue("Registration failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void login(String username, String password) {
        isLoading.setValue(true);
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        Call<LoginResponse> call = userApi.login(credentials);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getMessage() != null && loginResponse.getMessage().contains("Đăng nhập thành công")) {
                        loginMessageLiveData.setValue(loginResponse.getMessage());
                        if (loginResponse.getUser() != null) {
                            userLiveData.setValue(loginResponse.getUser());
                        }
                    } else {
                        String error = loginResponse.getError() != null ? loginResponse.getError() : "Unknown error";
                        errorMessage.setValue(error);
                    }
                } else {
                    errorMessage.setValue("Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }
}