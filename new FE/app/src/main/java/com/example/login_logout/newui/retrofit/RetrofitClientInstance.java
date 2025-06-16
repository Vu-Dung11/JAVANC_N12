package com.example.login_logout.newui.retrofit;

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
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
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

            // Configure Gson with the custom date adapter
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, dateTypeAdapter)
                    .setLenient()
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)  // timeout kết nối
                    .readTimeout(30, TimeUnit.SECONDS)     // timeout đọc dữ liệu
                    .writeTimeout(30, TimeUnit.SECONDS)    // timeout ghi dữ liệu
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)                // gán OkHttpClient với timeout
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
