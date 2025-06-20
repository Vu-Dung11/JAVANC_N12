package com.example.login_logout.data.Service;

import com.example.login_logout.data.api2.UserApi;
import com.example.login_logout.data.api2.HomestayApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    public static final String BASE_URL = "http://10.0.2.2:8080/";
    private static Retrofit retrofit = null;
    private static ApiServices instance;

    private ApiServices() {
        // Create custom date type adapter
        TypeAdapter<Date> dateTypeAdapter = new TypeAdapter<Date>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            @Override
            public void write(JsonWriter out, Date value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(dateFormat.format(value));
                }
            }

            @Override
            public Date read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String dateStr = in.nextString();
                try {
                    return dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    throw new IOException("Failed to parse date: " + dateStr, e);
                }
            }
        };

        // Tạo TypeAdapter cho LocalDateTime
        TypeAdapter<LocalDateTime> localDateTimeAdapter = new TypeAdapter<LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(formatter.format(value));
                }
            }

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String dateStr = in.nextString();
                return LocalDateTime.parse(dateStr, formatter);
            }
        };

        // Cấu hình Gson với TypeAdapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateTypeAdapter)
                .registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter)
                .setLenient()
                .create();

        // Tạo HttpLoggingInterceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Tạo OkHttpClient với interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    public static synchronized ApiServices getInstance() {
        if (instance == null) {
            instance = new ApiServices();
        }
        return instance;
    }

    public UserApi getUserApi() {
        return retrofit.create(UserApi.class);
    }

    public HomestayApi getHomestayApi() {
        return retrofit.create(HomestayApi.class);
    }
}