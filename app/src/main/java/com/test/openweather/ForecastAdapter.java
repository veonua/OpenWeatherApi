package com.test.openweather;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.openweather.myapplication.R;
import com.test.openweather.models.Forecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by veon on 6/25/16.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastHolder> {
    private final List<Forecast> list;

    public ForecastAdapter(ArrayList<Forecast> list) {
        this.list = list;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new ForecastHolder(li.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ForecastHolder extends RecyclerView.ViewHolder {
        private final Resources res;
        private final TextView nameView;
        private final TextView mainView;
        private final TextView detailView;
        private final ImageView iconView;
        private Forecast item;

        public ForecastHolder(View itemView) {
            super(itemView);
            res = itemView.getResources();
            nameView = (TextView) itemView.findViewById(R.id.name);
            mainView = (TextView) itemView.findViewById(R.id.main);
            detailView = (TextView) itemView.findViewById(R.id.description);
            iconView = (ImageView) itemView.findViewById(R.id.icon);
        }

        public void bind(@NonNull Forecast forecast) {
            item = forecast;
            nameView.setText(forecast.name);
            mainView.setText(res.getString(R.string.main_format, forecast.main.temp, forecast.weather[0].main));
            detailView.setText(forecast.weather[0].description);
            Picasso.with(nameView.getContext()).load(forecast.weather[0].getIconUri()).
                    into(iconView);
        }
    }
}
