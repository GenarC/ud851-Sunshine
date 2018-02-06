package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  TODO (1) Create a class called SunshineSyncTask
public class SunshineSyncTask{
    synchronized public static void syncWeather(Context c){
        try {
            URL weatherRequestUrl = NetworkUtils.getUrl(c);

            String weatherResp = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
            ContentValues[] wValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(c, weatherResp);

            if (wValues != null && wValues.length != 0) {
                ContentResolver cContentResolver = c.getContentResolver();
                cContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                cContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        wValues);
            }

        } catch (Exception ex) {
            Log.d("SunshineSyncTask", "Error#1");
        }
    }

//  TODO (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      TODO (3) Within syncWeather, fetch new weather data
//      TODO (4) If we have valid results, delete the old data and insert the new
}