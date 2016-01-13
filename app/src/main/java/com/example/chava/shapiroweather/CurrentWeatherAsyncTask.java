package com.example.chava.shapiroweather;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentWeatherAsyncTask extends AsyncTask<String, String, CurrentWeather> {

    private String zip;
    private CurrentWeather currentWeather;
    private RecyclerView.ViewHolder holder;

    public CurrentWeatherAsyncTask(String zip, RecyclerView.ViewHolder holder) {
        this.zip = zip;
        currentWeather = null;
        this.holder = holder;
    }


    @Override
    protected CurrentWeather doInBackground(String... params) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip +
                    "&appid=2de143494c0b295cca9337e1e96b00e0&units=imperial";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream in = connection.getInputStream();

            currentWeather = gson.fromJson(new InputStreamReader(in), CurrentWeather.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentWeather;
    }

    @Override
    protected void onPostExecute(CurrentWeather currentWeather) {
        super.onPostExecute(currentWeather);
        ((WeatherViewHolder) holder).bind(currentWeather);
    }
}
