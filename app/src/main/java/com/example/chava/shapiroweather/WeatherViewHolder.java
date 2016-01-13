package com.example.chava.shapiroweather;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView location;
    private TextView time;
    private Button addLocation;
    private ImageView icon;
    private TextView description;
    private TextView highLow;
    private TextView current;

    public WeatherViewHolder(View itemView, int viewType, Context context) {
        super(itemView);

        this.context = context;
        location = (TextView) itemView.findViewById(R.id.location);
        time = (TextView) itemView.findViewById(R.id.time);
        addLocation = (Button) itemView.findViewById(R.id.addLocation);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        description = (TextView) itemView.findViewById(R.id.description);
        highLow = (TextView) itemView.findViewById(R.id.highLow);
        current = (TextView) itemView.findViewById(R.id.currentTemp);
    }

    public void bind(CurrentWeather currentWeather) {
        location.setText(currentWeather.getName());
        DateFormat df = new SimpleDateFormat("h:mm a, z");
        String date = df.format(Calendar.getInstance().getTime());
        time.setText(date);
        addLocation.setText("\u002B");
        addLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Please enter zip");

                new AlertDialog.Builder(context)
                        .setTitle("Add Zip")
                        .setView(input)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Editable zipEditable = input.getText();
                                String zip = zipEditable.toString();
                                ((MainActivity) context).addZip(zip);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }).show();
            }
        });
        String iconURL = "http://openweathermap.org/img/w/" + currentWeather.getWeather().getIcon() + ".png";
        Picasso.with(context).load(iconURL).into(icon);
        description.setText(currentWeather.getWeather().getMain());
        highLow.setText("\u25b2" + String.valueOf(Math.round(currentWeather.getMain().getHigh())) + "\u00b0" +
                "\u25bc" + String.valueOf(Math.round(currentWeather.getMain().getLow())) + "\u00b0");
        current.setText(String.valueOf(Math.round(currentWeather.getMain().getTemp())) + "\u00b0");
    }

}
