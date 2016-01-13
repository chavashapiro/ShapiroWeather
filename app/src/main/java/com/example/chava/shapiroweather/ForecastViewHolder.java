package com.example.chava.shapiroweather;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ForecastViewHolder extends RecyclerView.ViewHolder {

    private TextView day;
    private ImageView icon;
    private TextView high;
    private TextView low;
    private int position;
    private Context context;

    public ForecastViewHolder(View itemView, int position, Context context) {
        super(itemView);
        day = (TextView) itemView.findViewById(R.id.day);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        high = (TextView) itemView.findViewById(R.id.high);
        low = (TextView) itemView.findViewById(R.id.low);
        this.position = position;
        this.context = context;
    }

    public void bind(DayForecast forecast) {
        GregorianCalendar today = new GregorianCalendar();
        today.add(Calendar.DAY_OF_MONTH, position);
        String[] weekdays = new DateFormatSymbols().getWeekdays();
        day.setText(weekdays[today.get(Calendar.DAY_OF_WEEK)]);
        String iconURL = "http://openweathermap.org/img/w/" + forecast.getWeather()[0].getIcon() + ".png";
        Picasso.with(context).load(iconURL).into(icon);
        high.setText(String.valueOf(Math.round(forecast.getTemp().getMax())) + "\u00b0");
        low.setText(String.valueOf(Math.round(forecast.getTemp().getMin())) + "\u00b0");

    }
}
