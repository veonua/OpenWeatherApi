package com.test.openweather.network;

import com.test.openweather.models.Forecast;
import com.test.openweather.models.OpenWeatherList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by veon on 6/25/16.
 */
public interface OpenWeather {
    @GET("forecast/city?id={id}")
    Call<Forecast> fetchForecastById(@Query("id") String id);

    @GET("weather?q={city}")
    Call<Forecast> fetchForecastByCityName(@Query("city") String city);

    @GET("group?units=metric")
    Call<OpenWeatherList<Forecast>> fetchForecasts(@Query("id") String ids);
}
