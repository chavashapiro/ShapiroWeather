package com.example.chava.shapiroweather;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherAdapter extends PagerAdapter {

    private ArrayList<String> zipCodes;
    private Context context;
    private WeatherForecast forecast;

    public WeatherAdapter(ArrayList<String> zips, Context context) {
        zipCodes = zips;
        this.context = context;
    }

    @Override
    public int getCount() {
        return zipCodes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.weather_pager_item, null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.locationWeather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        final WeatherRecycleViewAdapter adapter = new WeatherRecycleViewAdapter(context, zipCodes.get(position));

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").
                addConverterFactory(GsonConverterFactory.create()).build();
        WeatherForecastService weatherService = retrofit.create(WeatherForecastService.class);
        Call<WeatherForecast> call2 = weatherService.listWeatherForecast(zipCodes.get(position));
        call2.enqueue(new Callback<WeatherForecast>() {

            @Override
            public void onResponse(Response<WeatherForecast> response) {
                forecast = response.body();
                adapter.setWeatherForecast(forecast);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        recyclerView.setAdapter(adapter);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
