package com.test.openweather;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.openweather.error.ErrorHandler;
import com.test.openweather.error.SnackOnError;
import com.test.openweather.myapplication.R;
import com.test.openweather.models.City;
import com.test.openweather.models.Forecast;
import com.test.openweather.models.OpenWeatherList;
import com.test.openweather.network.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView list;

    public String getUid() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null) return null;

        return user.getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            signIn();
        } else {
            fetchConfig();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Here might be an append dialog, but now today", Snackbar.LENGTH_LONG)
                    .setAction("Is it sunny in Barcelona?", view1 -> addCity(3128760, "Barcelona"))
                    .show();
        });

        list = (RecyclerView) findViewById(R.id.list);
    }

    private void fetchConfig() {
        mDatabase.child("user-cities").child(getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Integer> ids = new ArrayList<>();

                if (dataSnapshot == null || dataSnapshot.getValue()==null) {
                    prefillDefaults();
                    return;
                }

                if (dataSnapshot.getChildrenCount() >= 0) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        City city = data.getValue(City.class);
                        ids.add(city.id);
                    }
                }

                if (ids.size()<1) return;
                Api.getInstance().fetchForecasts(TextUtils.join(",", ids))
                        .enqueue(new SnackOnError<OpenWeatherList<Forecast>>(list) {
                            @Override
                            public void onResponse(Call<OpenWeatherList<Forecast>> call, Response<OpenWeatherList<Forecast>> response) {
                                updateList(response.body());
                            }
                        });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private List<City> prefillDefaults() {
        List<City> res = new ArrayList<>();
        res.add(new City(2643743, "London"));
        res.add(new City(5368361, "Los Angeles"));
        res.add(new City(3435910, "Buenos Aires"));
        res.add(new City(1733046, "Kuala Lumpur"));
        res.add(new City(524901,  "Moscow"));
        res.add(new City(703448,  "Kiev"));
        res.add(new City(588409,  "Tallinn"));


        for (City city: res) {
            addCity(city.id, city.name);
        }
        return res;
    }

    private void updateList(OpenWeatherList<Forecast> body) {
        list.setAdapter(new ForecastAdapter(body.list));
    }

    private void signIn() {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnSuccessListener(this, authResult -> {
                    fetchConfig();
                })
                .addOnFailureListener(this, exception -> {
                    ErrorHandler.showSnack(list, exception, "");
                });
    }

    public void addCity(int id, String name) {
        String userId = getUid();
        City city = new City(id, name);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-cities/" + userId + "/" + id, city.toMap());
        mDatabase.updateChildren(childUpdates);
    }
}
