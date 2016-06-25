package com.test.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by veon on 6/25/16.
 */
public class OpenWeatherList<T> {
    public ArrayList<T> list;

    @SerializedName("cnt")
    public int count;
}
