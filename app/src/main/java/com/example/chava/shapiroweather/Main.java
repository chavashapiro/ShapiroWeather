package com.example.chava.shapiroweather;

import com.google.gson.annotations.SerializedName;

public class Main {
    private double temp;
    @SerializedName("temp_max")
    private double high;
    @SerializedName("temp_min")
    private double low;

    public double getTemp() {
        return this.temp;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

}
