package com.example.chava.shapiroweather;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherForecastService {
    @GET("/data/2.5/forecast/daily?&appid=2de143494c0b295cca9337e1e96b00e0&units=imperial&cnt=16")
    Call<WeatherForecast> listWeatherForecast(@Query("zip") String zip);

    @GET("/data/2.5/weather?&appid=2de143494c0b295cca9337e1e96b00e0&units=imperial")
    Call<CurrentWeather> listCurrentWeather(@Query("zip") String zip);
}
