package com.test.openweather.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class City {
    public String name;
    public int id;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("unused")
    public City() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);

        return result;
    }
}
