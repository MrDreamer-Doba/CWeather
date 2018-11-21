package com.test.mykhailodobosh.cweather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherData {

    @SerializedName("wind_cdir")
    @Expose
    private String windCdir;
    @SerializedName("rh")
    @Expose
    private double rh;
    @SerializedName("pod")
    @Expose
    private String pod;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("pres")
    @Expose
    private double pres;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("ob_time")
    @Expose
    private String obTime;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("clouds")
    @Expose
    private double clouds;
    @SerializedName("vis")
    @Expose
    private double vis;
    @SerializedName("wind_spd")
    @Expose
    private double windSpd;
    @SerializedName("wind_cdir_full")
    @Expose
    private String windCdirFull;
    @SerializedName("app_temp")
    @Expose
    private double appTemp;
    @SerializedName("state_code")
    @Expose
    private String stateCode;
    @SerializedName("ts")
    @Expose
    private double ts;
    @SerializedName("h_angle")
    @Expose
    private double hAngle;
    @SerializedName("dewpt")
    @Expose
    private double dewpt;
    @SerializedName("weather")
    @Expose
    private Weather weather = new Weather();
    @SerializedName("uv")
    @Expose
    private double uv;
    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("wind_dir")
    @Expose
    private double windDir;
    @SerializedName("elev_angle")
    @Expose
    private double elevAngle;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("precip")
    @Expose
    private double precip;
    @SerializedName("ghi")
    @Expose
    private double ghi;
    @SerializedName("dni")
    @Expose
    private double dni;
    @SerializedName("dhi")
    @Expose
    private double dhi;
    @SerializedName("solar_rad")
    @Expose
    private double solarRad;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("sunrise")
    @Expose
    private String sunrise;
    @SerializedName("sunset")
    @Expose
    private String sunset;
    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("slp")
    @Expose
    private double slp;

    public String getWindCdir() {
        return windCdir;
    }

    public void setWindCdir(String windCdir) {
        this.windCdir = windCdir;
    }

    public double getRh() {
        return rh;
    }

    public void setRh(double rh) {
        this.rh = rh;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public double getPres() {
        return pres;
    }

    public void setPres(double pres) {
        this.pres = pres;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getObTime() {
        return obTime;
    }

    public void setObTime(String obTime) {
        this.obTime = obTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public double getVis() {
        return vis;
    }

    public void setVis(double vis) {
        this.vis = vis;
    }

    public double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(double windSpd) {
        this.windSpd = windSpd;
    }

    public String getWindCdirFull() {
        return windCdirFull;
    }

    public void setWindCdirFull(String windCdirFull) {
        this.windCdirFull = windCdirFull;
    }

    public double getAppTemp() {
        return appTemp;
    }

    public void setAppTemp(double appTemp) {
        this.appTemp = appTemp;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public double getTs() {
        return ts;
    }

    public void setTs(double ts) {
        this.ts = ts;
    }

    public double getHAngle() {
        return hAngle;
    }

    public void setHAngle(double hAngle) {
        this.hAngle = hAngle;
    }

    public double getDewpt() {
        return dewpt;
    }

    public void setDewpt(double dewpt) {
        this.dewpt = dewpt;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getWindDir() {
        return windDir;
    }

    public void setWindDir(double windDir) {
        this.windDir = windDir;
    }

    public double getElevAngle() {
        return elevAngle;
    }

    public void setElevAngle(double elevAngle) {
        this.elevAngle = elevAngle;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public double getGhi() {
        return ghi;
    }

    public void setGhi(double ghi) {
        this.ghi = ghi;
    }

    public double getDni() {
        return dni;
    }

    public void setDni(double dni) {
        this.dni = dni;
    }

    public double getDhi() {
        return dhi;
    }

    public void setDhi(double dhi) {
        this.dhi = dhi;
    }

    public double getSolarRad() {
        return solarRad;
    }

    public void setSolarRad(double solarRad) {
        this.solarRad = solarRad;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public double getSlp() {
        return slp;
    }

    public void setSlp(double slp) {
        this.slp = slp;
    }

}