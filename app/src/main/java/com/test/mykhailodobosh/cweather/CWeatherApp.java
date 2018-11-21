package com.test.mykhailodobosh.cweather;

import android.app.Application;
import android.content.Context;

import com.test.mykhailodobosh.cweather.network.IWeatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CWeatherApp extends Application {

    private static Context sContext;
    private static IWeatherApi sWeatherApi;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherbit.io/v2.0/current/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sWeatherApi = mRetrofit.create(IWeatherApi.class);
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static IWeatherApi getWeatherApi() {
        return sWeatherApi;
    }
}
