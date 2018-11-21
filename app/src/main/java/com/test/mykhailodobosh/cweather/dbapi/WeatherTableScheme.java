package com.test.mykhailodobosh.cweather.dbapi;

public class WeatherTableScheme {
    public static final String TABLE_NAME = "CityWeather";

    public static final class Column {
        public static final String ID          = "id";
        public static final String CITY        = "city";
        public static final String COUNTRY     = "country_code";
        public static final String SYNCTIME    = "synctime";
        public static final String ICON        = "icon";
        public static final String DESCRIPTION = "description";
        public static final String HUMIDITY    = "humidity";
        public static final String SUNRISE     = "sunrise";
        public static final String SUNSET      = "sunset";
        public static final String TEMP        = "temperature";
    }
}
