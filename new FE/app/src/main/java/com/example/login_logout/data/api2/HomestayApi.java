package com.example.login_logout.data.api2;

import com.example.login_logout.data.model2.HomestayDTO;
import com.example.login_logout.data.model2.HomestaySummaryDTO;
import com.example.login_logout.data.model2.PhotoDTO;
import com.example.login_logout.data.model2.RoomDTO;
import com.example.login_logout.data.model2.SearchHomestayDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Interface định nghĩa các API endpoint liên quan đến quản lý homestay
 * Bao gồm: thông tin homestay, phòng, hình ảnh và dịch vụ
 */
public interface HomestayApi {
    /**
     * Lấy danh sách tất cả homestay
     * @return Danh sách homestay
     */
    @GET("api/homestays")
    Call<List<HomestayDTO>> getAllHomestays();

    /**
     * Lấy thông tin homestay theo ID
     * @param id ID của homestay
     * @return Thông tin homestay
     */
    @GET("api/homestays/{id}")
    Call<HomestayDTO> getHomestayById(@Path("id") Long id);

    /**
     * Lấy danh sách homestay của một chủ sở hữu
     * @param userId ID của chủ sở hữu
     * @return Danh sách homestay
     */
    @GET("api/homestays/user/{userId}")
    Call<List<HomestayDTO>> getHomestaysByUser(@Path("userId") Long userId);

    /**
     * Tạo homestay mới
     * @param homestay Thông tin homestay cần tạo
     * @return Thông tin homestay đã tạo
     */
    @POST("api/homestays")
    Call<HomestayDTO> createHomestay(@Body HomestayDTO homestay);

    /**
     * Cập nhật thông tin homestay
     * @param homestayId ID của homestay cần cập nhật
     * @param homestay Thông tin mới của homestay
     * @return Thông tin homestay sau khi cập nhật
     */
    @PUT("api/homestays/{id}")
    Call<HomestayDTO> updateHomestay(@Path("id") Long homestayId, @Body HomestayDTO homestay);

    /**
     * Xóa homestay
     * @param homestayId ID của homestay cần xóa
     */
    @DELETE("api/homestays/{id}")
    Call<Void> deleteHomestay(@Path("id") Long homestayId);

    /**
     * Lấy danh sách phòng của một homestay
     * @param homestayId ID của homestay
     * @return Danh sách phòng
     */
    @GET("api/homestays/{homestayId}/rooms")
    Call<List<RoomDTO>> getRoomsByHomestay(@Path("homestayId") Long homestayId);

    /**
     * Thêm phòng mới vào homestay
     * @param homestayId ID của homestay
     * @param room Thông tin phòng cần thêm
     * @return Thông tin phòng đã thêm
     */
    @POST("api/homestays/{homestayId}/rooms")
    Call<RoomDTO> createRoom(@Path("homestayId") Long homestayId, @Body RoomDTO room);

    /**
     * Cập nhật thông tin phòng
     * @param roomId ID của phòng cần cập nhật
     * @param room Thông tin mới của phòng
     * @return Thông tin phòng sau khi cập nhật
     */
    @PUT("api/rooms/{id}")
    Call<RoomDTO> updateRoom(@Path("id") Long roomId, @Body RoomDTO room);

    /**
     * Xóa phòng
     * @param roomId ID của phòng cần xóa
     */
    @DELETE("api/rooms/{id}")
    Call<Void> deleteRoom(@Path("id") Long roomId);

    /**
     * Lấy danh sách hình ảnh của một homestay
     * @param homestayId ID của homestay
     * @return Danh sách hình ảnh
     */
    @GET("api/homestays/{homestayId}/photos")
    Call<List<PhotoDTO>> getPhotosByHomestay(@Path("homestayId") Long homestayId);

    /**
     * Thêm hình ảnh mới vào homestay
     * @param homestayId ID của homestay
     * @param photo Thông tin hình ảnh cần thêm
     * @return Thông tin hình ảnh đã thêm
     */
    @POST("api/homestays/{homestayId}/photos")
    Call<PhotoDTO> uploadPhoto(@Path("homestayId") Long homestayId, @Body PhotoDTO photo);

    /**
     * Xóa hình ảnh
     * @param photoId ID của hình ảnh cần xóa
     */
    @DELETE("api/photos/{id}")
    Call<Void> deletePhoto(@Path("id") Long photoId);

    /**
     * Lấy danh sách dịch vụ của một homestay
     * @param homestayId ID của homestay
     * @return Danh sách dịch vụ
     */
//    @GET("api/homestays/{homestayId}/services")
//    Call<List<ServiceAdvantageDTO>> getServicesByHomestay(@Path("homestayId") Long homestayId);

    /**
     * Thêm dịch vụ mới vào homestay
     * @param homestayId ID của homestay
     * @param service Thông tin dịch vụ cần thêm
     * @return Thông tin dịch vụ đã thêm
     */
//    @POST("api/homestays/{homestayId}/services")
//    Call<ServiceAdvantageDTO> addService(@Path("homestayId") Long homestayId, @Body ServiceAdvantageDTO service);

    /**
     * Xóa dịch vụ khỏi homestay
     * @param homestayId ID của homestay
     * @param serviceId ID của dịch vụ cần xóa
     */
    @DELETE("api/homestays/{homestayId}/services/{serviceId}")
    Call<Void> removeService(@Path("homestayId") Long homestayId, @Path("serviceId") Long serviceId);

    /**
     * Tìm kiếm và lọc homestay theo các tiêu chí
     * @param keyword Từ khóa tìm kiếm
     * @param province Tỉnh/thành phố
     * @param district Quận/huyện
     * @param ward Phường/xã
     * @param minPrice Giá tối thiểu
     * @param maxPrice Giá tối đa
     * @return Danh sách homestay thỏa mãn điều kiện
     */
    @GET("api/homestays/search")
    Call<List<HomestayDTO>> searchHomestays(
        @Query("keyword") String keyword,
        @Query("province") String province,
        @Query("district") String district,
        @Query("ward") String ward,
        @Query("minPrice") Double minPrice,
        @Query("maxPrice") Double maxPrice
    );


    @GET("api/homestays/summary/search/keyword")
    Call<List<SearchHomestayDTO>> searchHomestaysByKeyword(@Query("keyword") String keyword);
    @GET("api/homestays/summary")
    Call<List<HomestaySummaryDTO>> getHomestaySummaries();




} 