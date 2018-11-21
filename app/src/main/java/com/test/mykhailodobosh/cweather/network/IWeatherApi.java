package com.test.mykhailodobosh.cweather.network;

import com.test.mykhailodobosh.cweather.models.ForecastWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherApi {
    @GET("?&key=b2b74687b86e400cb263afce42bde7be")
    Call<ForecastWeather> getCurrentWeather(@Query("lat") String lat, @Query("lon") String lon);
}
