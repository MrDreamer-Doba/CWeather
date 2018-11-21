package com.test.mykhailodobosh.cweather.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.mykhailodobosh.cweather.CWeatherApp;
import com.test.mykhailodobosh.cweather.R;
import com.test.mykhailodobosh.cweather.dbapi.WeatherDatabase;
import com.test.mykhailodobosh.cweather.models.WeatherData;
import com.test.mykhailodobosh.cweather.models.ForecastWeather;
import com.test.mykhailodobosh.cweather.utils.NetworkUtil;
import com.test.mykhailodobosh.cweather.utils.UiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.test.mykhailodobosh.cweather.screens.MapActivity.PREF_LAT;
import static com.test.mykhailodobosh.cweather.screens.MapActivity.PREF_LON;
import static com.test.mykhailodobosh.cweather.screens.MapActivity.PREF_IS_FIRST_TIME;
import static com.test.mykhailodobosh.cweather.screens.MapActivity.WEATHER_SETTINGS;

public class WeatherDetailedActivity extends AppCompatActivity {

    private ForecastWeather mWeather;
    private Context mContext;
    private boolean mIsFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);
        mContext = this;

        final SharedPreferences preferences = getSharedPreferences(WEATHER_SETTINGS, Context.MODE_PRIVATE);
        mIsFirstTime = preferences.getBoolean(PREF_IS_FIRST_TIME, true);
        float lat = preferences.getFloat(PREF_LAT, 0);
        float lon = preferences.getFloat(PREF_LON, 0);

        if (!NetworkUtil.isNetworkAvailable(this)) {
            updateUI();
            Snackbar.make(findViewById(R.id.coordinator_layout),
                    "No internet connection!",
                    Snackbar.LENGTH_LONG).show();
        } else {
            updateUI();
            CWeatherApp.getWeatherApi().getCurrentWeather(
                    String.valueOf(lat), String.valueOf(lon))
                    .enqueue(new Callback<ForecastWeather>() {
                        @Override
                        public void onResponse(@NonNull Call<ForecastWeather> call, @NonNull Response<ForecastWeather> response) {
                            if (response.isSuccessful()) {
                                mWeather = response.body();
                                Toast.makeText(mContext, "Weather Updated", Toast.LENGTH_LONG).show();
                                storeCurrentWeather(mWeather.getData().get(0));
                                updateUI();
                                preferences.edit().putBoolean(PREF_IS_FIRST_TIME, false).apply();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ForecastWeather> call, @NonNull Throwable t) {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void updateUI() {
        WeatherData record = WeatherDatabase.getInstance().getWeatherRecord();

        if (record == null) {
            return;
        }

        String location = String.format("%s, %s", record.getCityName(), record.getCountryCode());
        TextView cityNameTextView = findViewById(R.id.tv_city);
        cityNameTextView.setText(location);

        String lastSync = String.format("Last update: %s", record.getObTime());
        TextView lastSyncTextView = findViewById(R.id.tv_sync_time);
        lastSyncTextView.setText(lastSync);

        ImageView iconImageView = findViewById(R.id.ic_weather);
        iconImageView.setBackgroundResource(
                UiUtil.sIconsMap.get(record.getWeather().getIcon()));

        TextView weatherDescriptionTextView = findViewById(R.id.tv_weather_description);
        weatherDescriptionTextView.setText(record.getWeather().getDescription());

        @SuppressLint("DefaultLocale")
        String humidity = String.format("Humidity: %d%%", Math.round(record.getRh()));
        TextView humidityTextView = findViewById(R.id.tv_humidity);
        humidityTextView.setText(humidity);

        String sunRiseSet = String.format("Sunrise: %s / Sunset: %s",
                record.getSunset(), record.getSunrise());
        TextView sunRiseSetTextView = findViewById(R.id.tv_sun_rise_set);
        sunRiseSetTextView.setText(sunRiseSet);

        @SuppressLint("DefaultLocale")
        String tempCelsius = String.format("%d °C", Math.round(record.getTemp()));
        TextView tempTextView = findViewById(R.id.tv_temp);
        tempTextView.setText(tempCelsius);
    }

    private void storeCurrentWeather(WeatherData record) {
        if (mIsFirstTime) {
            WeatherDatabase.getInstance().insertWeatherRecord(record);
        } else {
            WeatherDatabase.getInstance().updateWeatherRecord(record);
        }
    }
}
