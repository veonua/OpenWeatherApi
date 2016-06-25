package com.test.openweather.models;

import android.net.Uri;

/**
 * Created by veon on 6/25/16.
 */
public class Forecast {
    public String name;
    public int id;
    public Weather[] weather;
    public Main main;
    public Wind wind;
    public Clouds clouds;

    public static class Weather {
        int id;
        public String main;
        public String description;
        String icon;

        public Uri getIconUri() {
            return Uri.parse("http://openweathermap.org/img/w/"+icon+".png");
        }
    }

    public static class Main {
        public float temp,temp_min, temp_max;
        public int pressure;
        public int humidity;
    }

    public static class Wind {
        public float speed;
        public float deg;
    }

    public static class Clouds {
        int all;
    }
}
