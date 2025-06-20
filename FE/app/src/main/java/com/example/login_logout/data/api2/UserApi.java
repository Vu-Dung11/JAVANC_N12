package com.example.login_logout.data.api2;

import com.example.login_logout.data.model2.UserDTO;
import com.example.login_logout.data.model2.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

/**
 * Interface định nghĩa các API endpoint liên quan đến quản lý người dùng
 * Bao gồm: xác thực, quản lý thông tin người dùng và quản lý mật khẩu
 */
public interface UserApi {
    /**
     * Đăng nhập vào hệ thống
     * @param credentials Map chứa thông tin đăng nhập (username/email và password)
     * @return Chuỗi thông báo đăng nhập thành công hoặc lỗi
     */
    @POST("api/users/login")
    Call<LoginResponse> login(@Body Map<String, String> credentials);

    /**
     * Đăng ký tài khoản mới
     * @param user Thông tin người dùng cần đăng ký
     * @return Thông tin người dùng đã đăng ký
     */
    @POST("api/users")
    Call<UserDTO> register(@Body UserDTO user);

    /**
     * Lấy danh sách tất cả người dùng
     * @return Danh sách người dùng
     */
    @GET("api/users")
    Call<List<UserDTO>> getAllUsers();

    /**
     * Lấy thông tin người dùng theo ID
     * @param userId ID của người dùng
     * @return Thông tin người dùng
     */
    @GET("api/users/{id}")
    Call<UserDTO> getUserById(@Path("id") Long userId);

    /**
     * Lấy thông tin người dùng theo username
     * @param username Tên người dùng
     * @return Thông tin người dùng
     */
    @GET("api/users/username/{username}")
    Call<UserDTO> getUserByUserName(@Path("username") String username);

    /**
     * Cập nhật thông tin người dùng
     * @param userId ID của người dùng cần cập nhật
     * @param user Thông tin mới của người dùng
     * @return Thông tin người dùng sau khi cập nhật
     */
    @POST("api/users/{id}")
    Call<UserDTO> updateUser(@Path("id") Long userId, @Body UserDTO user);

    /**
     * Xóa người dùng
     * @param userId ID của người dùng cần xóa
     */
    @POST("api/users/{id}")
    Call<Void> deleteUser(@Path("id") Long userId);

    /**
     * Cập nhật thông tin cá nhân của người dùng đang đăng nhập
     * @param user Thông tin mới cần cập nhật
     * @return Thông tin người dùng sau khi cập nhật
     */
    @POST("api/users/profile")
    Call<UserDTO> updateProfile(@Body UserDTO user);

    /**
     * Thay đổi mật khẩu
     * @param passwordData Map chứa mật khẩu cũ và mật khẩu mới
     */
    @POST("api/users/change-password")
    Call<Void> changePassword(@Body Map<String, String> passwordData);

    /**
     * Yêu cầu khôi phục mật khẩu
     * @param emailData Map chứa email cần khôi phục mật khẩu
     */
    @POST("api/users/forgot-password")
    Call<Void> forgotPassword(@Body Map<String, String> emailData);

    /**
     * Đặt lại mật khẩu mới
     * @param resetData Map chứa token reset và mật khẩu mới
     */
    @POST("api/users/reset-password")
    Call<Void> resetPassword(@Body Map<String, String> resetData);
}