package com.example.chava.shapiroweather;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SharedPreferences preferences;
    private ArrayList<String> zipCodes;
    private LocationManager locationManager;
    private WeatherAdapter adapter;
    private String gpsZip;

    @Override
    protected void onResume() {
        super.onResume();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = null;

                    if (addresses != null && addresses.size() > 0) {
                        address = addresses.get(0);
                        gpsZip = address.getPostalCode();
                    }

                    if (!zipCodes.contains(gpsZip)) {
                        zipCodes.add(gpsZip);
                        adapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        String zips = preferences.getString("ZIPS", "");
        String[] zipTokens = zips.split(" ");
        for (int i = 1; i < zipTokens.length; i++) {
            String zip = zipTokens[i];
            if (!zipCodes.contains(zip)) {
                zipCodes.add(zip);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        zipCodes = new ArrayList<String>();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new WeatherAdapter(zipCodes, this);
        viewPager.setAdapter(adapter);
    }

    public void addZip(String zip) {
        if (!zipCodes.contains(zip)) {
            zipCodes.add(zip);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < zipCodes.size(); i++) {
            builder.append(zipCodes.get(i) + " ");
        }

        String zips = builder.toString().trim();
        editor.putString("ZIPS", zips);

        editor.apply();
    }
}
