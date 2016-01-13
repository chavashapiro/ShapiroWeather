package com.example.chava.shapiroweather;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CurrentWeather currentWeather;
    private WeatherForecast forecast;
    private Context context;
    private String zip;


    public WeatherRecycleViewAdapter(Context context, String zip) {
        this.context = context;
        this.zip = zip;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return position;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.current_weather_item, parent, false);
            itemView.setMinimumHeight(parent.getMeasuredHeight());
            return new WeatherViewHolder(itemView, viewType, context);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.weather_forecast_item, parent, false);
            return new ForecastViewHolder(itemView, viewType, context);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            CurrentWeatherAsyncTask task = new CurrentWeatherAsyncTask(zip, holder);
            task.execute();
        } else {
            ((ForecastViewHolder) holder).bind(forecast.getList()[position]);
        }
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setWeatherForecast(WeatherForecast forecast) {
        this.forecast = forecast;
    }
}
