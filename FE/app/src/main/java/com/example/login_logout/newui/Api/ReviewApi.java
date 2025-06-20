    package com.example.login_logout.newui.Api;

    import com.example.login_logout.newui.Model.Homestay;

    import java.util.List;

    import retrofit2.Call;
    import retrofit2.http.GET;
    import retrofit2.http.Path;

    public interface ReviewApi {
        @GET("/api/reviews/AverageRateHomestay/{homestayId}")
        Call<Double> getAverageRate(@Path("homestayId") Long homestayId);

        @GET("/api/reviews/getHomestayByHighRate")
        Call<List<Homestay>> getHomestayByHighRate();
    }
