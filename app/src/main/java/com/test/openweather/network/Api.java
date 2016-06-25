package com.test.openweather.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by veon on 6/25/16.
 */
public class Api {
    private static OpenWeather ourInstance;
    static {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AppKeyInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ourInstance = retrofit.create(OpenWeather.class);
    }

    public static OpenWeather getInstance() {
        return ourInstance;
    }

    private static class AppKeyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("appid","49f151dd5d4461bcf4246b385b0cc075").build();
            request = request.newBuilder().url(url).build();

            return chain.proceed(request);
        }
    }
}
