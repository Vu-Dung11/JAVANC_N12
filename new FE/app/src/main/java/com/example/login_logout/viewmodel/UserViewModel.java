package com.example.login_logout.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.login_logout.data.Service.ApiServices;
import com.example.login_logout.data.api2.UserApi;
import com.example.login_logout.data.model2.LoginResponse;
import com.example.login_logout.data.model2.UserDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";
    private MutableLiveData<UserDTO> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> loginMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final UserApi userApi;
    private final Gson gson = new Gson();

    public UserViewModel(@NonNull Application application) {
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
        Log.d(TAG, "Sending register request: " + gson.toJson(userDTO));
        Call<UserDTO> call = userApi.register(userDTO);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Register success - Code: " + response.code() + ", Response: " + gson.toJson(response.body()));
                    userLiveData.setValue(response.body());
                } else {
                    String error = parseErrorResponse(response);
                    Log.e(TAG, "Register failed - Code: " + response.code() + ", Error: " + error);
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                isLoading.setValue(false);
                Log.e(TAG, "Register network error: " + t.getMessage(), t);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void login(String username, String password) {
        isLoading.setValue(true);
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        Log.d(TAG, "Sending login request: " + gson.toJson(credentials));
        Call<LoginResponse> loginCall = userApi.login(credentials);
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getMessage() != null && loginResponse.getMessage().contains("Đăng nhập thành công")) {
                        Log.d(TAG, "Login success - Code: " + response.code() + ", Response: " + loginResponse.getMessage());
                        loginMessageLiveData.setValue(loginResponse.getMessage());
                        if (loginResponse.getUser() != null) {
                            Log.d(TAG, "User data received: " + gson.toJson(loginResponse.getUser()));
                            userLiveData.setValue(loginResponse.getUser());
                        } else {
                            Log.w(TAG, "Login successful but no user data received");
                            errorMessage.setValue("No user data received");
                        }
                    } else {
                        String error = loginResponse.getError() != null ? loginResponse.getError() : "Unknown error";
                        Log.e(TAG, "Login failed - Code: " + response.code() + ", Error: " + error);
                        errorMessage.setValue(error);
                    }
                } else {
                    String error = parseErrorResponse(response);
                    Log.e(TAG, "Login failed - Code: " + response.code() + ", Error: " + error);
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                isLoading.setValue(false);
                Log.e(TAG, "Login network error: " + t.getMessage(), t);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }

    private void fetchUserByUsername(String username) {
        Log.d(TAG, "Fetching user by username: " + username);
        Call<UserDTO> userCall = userApi.getUserByUserName(username);
        userCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Fetch user success - Code: " + response.code() + ", Response: " + gson.toJson(response.body()));
                    userLiveData.setValue(response.body());
                } else {
                    String error = parseErrorResponse(response);
                    Log.e(TAG, "Fetch user failed - Code: " + response.code() + ", Error: " + error);
                    errorMessage.setValue(error);
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                isLoading.setValue(false);
                Log.e(TAG, "Fetch user network error: " + t.getMessage(), t);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }

    private String parseErrorResponse(Response<?> response) {
        String error = "Unknown error - Code: " + response.code();
        if (response.errorBody() != null) {
            try {
                error = response.errorBody().string();
                Log.d(TAG, "Parsed error body: " + error);
            } catch (IOException e) {
                Log.e(TAG, "Error parsing error body: " + e.getMessage(), e);
                error = "Error processing response: " + response.message();
            }
        } else {
            error = "Error: " + response.message();
        }
        return error;
    }
}