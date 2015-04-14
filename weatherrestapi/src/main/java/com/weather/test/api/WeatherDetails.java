package com.weather.test.api;

import java.io.Serializable;

/**
 * Created by neeraj on 4/13/15.
 *
 * This class contains all the information for the weather for the selected zip code.
 */
public class WeatherDetails implements Serializable {

    private String mLocationName;
    private double mLocationLatitude;
    private double mLocationLongitude;
    private String mWeatherCondition;
    private String mWeatherDescription;
    private int mCurrentTemperature;
    private int mMinimumTemperature;
    private int mMaximumTemperature;
    private int mPressure;
    private int mHumidity;
    private int mWindSpeed;

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String locationName) {
        this.mLocationName = locationName;
    }

    public double getLocationLatitude() {
        return mLocationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.mLocationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return mLocationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.mLocationLongitude = locationLongitude;
    }

    public String getWeatherCondition() {
        return mWeatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.mWeatherCondition = weatherCondition;
    }

    public String getWeatherDescription() {
        return mWeatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.mWeatherDescription = weatherDescription;
    }

    public int getCurrentTemperature() {
        return mCurrentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.mCurrentTemperature = currentTemperature;
    }

    public int getMinimumTemperature() {
        return mMinimumTemperature;
    }

    public void setMinimumTemperature(int minimumTemperature) {
        this.mMinimumTemperature = minimumTemperature;
    }

    public int getMaximumTemperature() {
        return mMaximumTemperature;
    }

    public void setMaximumTemperature(int maximumTemperature) {
        this.mMaximumTemperature = maximumTemperature;
    }

    public int getPressure() {
        return mPressure;
    }

    public void setPressure(int pressure) {
        this.mPressure = pressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public void setHumidity(int humidity) {
        this.mHumidity = humidity;
    }

    public int getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.mWindSpeed = windSpeed;
    }
}
