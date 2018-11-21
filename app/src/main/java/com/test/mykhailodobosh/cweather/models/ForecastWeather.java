package com.test.mykhailodobosh.cweather.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastWeather {

    @SerializedName("data")
    @Expose
    private List<WeatherData> data = null;
    @SerializedName("count")
    @Expose
    private int count;

    public List<WeatherData> getData() {
        return data;
    }

    public void setData(List<WeatherData> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}