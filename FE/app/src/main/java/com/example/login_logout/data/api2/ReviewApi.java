package com.example.login_logout.data.api2;

import com.example.login_logout.data.model2.ReviewDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * Interface định nghĩa các API endpoint liên quan đến quản lý đánh giá
 * Bao gồm: tạo, cập nhật, xóa và xem thông tin đánh giá
 */
public interface ReviewApi {
    /**
     * Lấy danh sách tất cả đánh giá
     * @return Danh sách đánh giá
     */
    @GET("api/reviews")
    Call<List<ReviewDTO>> getAllReviews();

    /**
     * Lấy thông tin đánh giá theo ID
     * @param reviewId ID của đánh giá
     * @return Thông tin đánh giá
     */
    @GET("api/reviews/{id}")
    Call<ReviewDTO> getReviewById(@Path("id") Long reviewId);

    /**
     * Lấy danh sách đánh giá của một homestay
     * @param homestayId ID của homestay
     * @return Danh sách đánh giá
     */
    @GET("api/reviews/homestay/{homestayId}")
    Call<List<ReviewDTO>> getReviewsByHomestay(@Path("homestayId") Long homestayId);

    /**
     * Lấy danh sách đánh giá của một người dùng
     * @param userId ID của người dùng
     * @return Danh sách đánh giá
     */
    @GET("api/reviews/user/{userId}")
    Call<List<ReviewDTO>> getReviewsByUser(@Path("userId") Long userId);

    /**
     * Tạo đánh giá mới
     * @param review Thông tin đánh giá cần tạo
     * @return Thông tin đánh giá đã tạo
     */
    @POST("api/reviews")
    Call<ReviewDTO> createReview(@Body ReviewDTO review);

    /**
     * Cập nhật thông tin đánh giá
     * @param reviewId ID của đánh giá cần cập nhật
     * @param review Thông tin mới của đánh giá
     * @return Thông tin đánh giá sau khi cập nhật
     */
    @PUT("api/reviews/{id}")
    Call<ReviewDTO> updateReview(@Path("id") Long reviewId, @Body ReviewDTO review);

    /**
     * Xóa đánh giá
     * @param reviewId ID của đánh giá cần xóa
     */
    @DELETE("api/reviews/{id}")
    Call<Void> deleteReview(@Path("id") Long reviewId);

    /**
     * Lấy điểm đánh giá trung bình của một homestay
     * @param homestayId ID của homestay
     * @return Điểm đánh giá trung bình
     */
    @GET("api/reviews/homestay/{homestayId}/average-rating")
    Call<Double> getAverageRating(@Path("homestayId") Long homestayId);

    /**
     * Lấy phân bố điểm đánh giá của một homestay
     * @param homestayId ID của homestay
     * @return Map với key là điểm số (1-5) và value là số lượng đánh giá
     */
    @GET("api/reviews/homestay/{homestayId}/rating-distribution")
    Call<Map<Integer, Integer>> getRatingDistribution(@Path("homestayId") Long homestayId);
} 