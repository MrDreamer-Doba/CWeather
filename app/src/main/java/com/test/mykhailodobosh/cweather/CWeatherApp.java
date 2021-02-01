package com.test.mykhailodobosh.cweather;

import android.app.Application;
import android.content.Context;

import com.test.mykhailodobosh.cweather.network.IWeatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CWeatherApp extends Application {

    private static Context context;
    private static IWeatherApi weatherApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherbit.io/v2.0/current/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(IWeatherApi.class);
    }

    public static Context getAppContext() {
        return context;
    }

    public static IWeatherApi getWeatherApi() {
        return weatherApi;
    }
}
