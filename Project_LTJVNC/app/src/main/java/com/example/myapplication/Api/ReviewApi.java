    package com.example.myapplication.Api;

    import com.example.myapplication.Model.Homestay;

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
