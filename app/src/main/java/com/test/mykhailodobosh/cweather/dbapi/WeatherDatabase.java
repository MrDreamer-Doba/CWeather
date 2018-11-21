package com.test.mykhailodobosh.cweather.dbapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.mykhailodobosh.cweather.CWeatherApp;
import com.test.mykhailodobosh.cweather.models.WeatherData;

public class WeatherDatabase extends SQLiteOpenHelper {
    private static WeatherDatabase instance;
    private static final String TAG = WeatherDatabase.class.getSimpleName();
    private static final String DB_NAME = "weather";
    private static final int DB_VERSION = +1;

    private WeatherDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static WeatherDatabase getInstance() {
        if (instance == null) {
            instance = new WeatherDatabase(CWeatherApp.getAppContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createWeatherTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (deleteTable(db, WeatherTableScheme.TABLE_NAME)) {
            onCreate(db);
            db.close();
        }
    }

    private void createWeatherTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + WeatherTableScheme.TABLE_NAME + " (" +
                WeatherTableScheme.Column.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherTableScheme.Column.CITY + " TEXT, " +
                WeatherTableScheme.Column.COUNTRY + " TEXT, " +
                WeatherTableScheme.Column.SYNCTIME + " TEXT, " +
                WeatherTableScheme.Column.ICON + " TEXT, " +
                WeatherTableScheme.Column.DESCRIPTION + " TEXT, " +
                WeatherTableScheme.Column.HUMIDITY + " TEXT, " +
                WeatherTableScheme.Column.SUNRISE + " TEXT, " +
                WeatherTableScheme.Column.SUNSET + " TEXT, " +
                WeatherTableScheme.Column.TEMP + " TEXT)";

        try {
            db.execSQL(sql);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private boolean deleteTable(SQLiteDatabase db, String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;

        boolean result = true;

        try {
            db.execSQL(sql);
        } catch (Exception e) {
            result = false;
            Log.d(TAG, e.getMessage());
        }

        return result;
    }

    public void insertWeatherRecord(WeatherData record) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(WeatherTableScheme.Column.CITY,        record.getCityName());
        cv.put(WeatherTableScheme.Column.COUNTRY,     record.getCountryCode());
        cv.put(WeatherTableScheme.Column.SYNCTIME,    record.getObTime());
        cv.put(WeatherTableScheme.Column.ICON,        record.getWeather().getIcon());
        cv.put(WeatherTableScheme.Column.DESCRIPTION, record.getWeather().getDescription());
        cv.put(WeatherTableScheme.Column.HUMIDITY,    record.getRh());
        cv.put(WeatherTableScheme.Column.SUNRISE,     record.getSunrise());
        cv.put(WeatherTableScheme.Column.SUNSET,      record.getSunset());
        cv.put(WeatherTableScheme.Column.TEMP,        record.getTemp());

        try {
            db.insert(WeatherTableScheme.TABLE_NAME, null, cv);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        db.close();
    }

    public void updateWeatherRecord(WeatherData record) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(WeatherTableScheme.Column.CITY,        record.getCityName());
        cv.put(WeatherTableScheme.Column.COUNTRY,     record.getCountryCode());
        cv.put(WeatherTableScheme.Column.SYNCTIME,    record.getObTime());
        cv.put(WeatherTableScheme.Column.ICON,        record.getWeather().getIcon());
        cv.put(WeatherTableScheme.Column.DESCRIPTION, record.getWeather().getDescription());
        cv.put(WeatherTableScheme.Column.HUMIDITY,    record.getRh());
        cv.put(WeatherTableScheme.Column.SUNRISE,     record.getSunrise());
        cv.put(WeatherTableScheme.Column.SUNSET,      record.getSunset());
        cv.put(WeatherTableScheme.Column.TEMP,        record.getTemp());

        String whereClause = WeatherTableScheme.Column.ID + " = (SELECT MIN(" + WeatherTableScheme.Column.ID + ") " +
                "FROM " + WeatherTableScheme.TABLE_NAME + ")";

        try {
            db.update(WeatherTableScheme.TABLE_NAME, cv, whereClause, null) ;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        db.close();
    }

    public WeatherData getWeatherRecord() {
        WeatherData weatherRecord = new WeatherData();

        String[] columns = new String[]{
                WeatherTableScheme.Column.CITY,
                WeatherTableScheme.Column.COUNTRY,
                WeatherTableScheme.Column.SYNCTIME,
                WeatherTableScheme.Column.ICON,
                WeatherTableScheme.Column.DESCRIPTION,
                WeatherTableScheme.Column.HUMIDITY,
                WeatherTableScheme.Column.SUNRISE,
                WeatherTableScheme.Column.SUNSET,
                WeatherTableScheme.Column.TEMP,
        };
        String selection = WeatherTableScheme.Column.ID + " = ?";
        String[] selectionArgs = new String[]{ "1" };

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(WeatherTableScheme.TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);
        if (cursor.getCount() == 0) {
            return null;
        }

        if (cursor.moveToFirst()) {
            do {
                weatherRecord.setCityName(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.CITY)));
                weatherRecord.setCountryCode(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.COUNTRY)));
                weatherRecord.setObTime(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.SYNCTIME)));
                weatherRecord.getWeather().setIcon(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.ICON)));
                weatherRecord.getWeather().setDescription(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.DESCRIPTION)));
                weatherRecord.setRh(cursor.getInt(cursor.getColumnIndex(WeatherTableScheme.Column.HUMIDITY)));
                weatherRecord.setSunrise(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.SUNRISE)));
                weatherRecord.setSunset(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.SUNSET)));
                weatherRecord.setTemp(Double.parseDouble(cursor.getString(cursor.getColumnIndex(WeatherTableScheme.Column.TEMP))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return weatherRecord;
    }
}
