package com.weather.test.api;

/**
 * Created by neeraj on 4/13/15.
 *
 * This interface handles the post execution when the data has been fetched from the server.
 */
public interface OnWeatherProcessListener {

    /**
     * This is called when the data fetched from the server has been parsed and encapsulated in an object.
     *
     * @param weatherDetails - the weather details object containing all the values.
     */
    void onWeatherDetailsReceived(WeatherDetails weatherDetails);

    /**
     * This is called when an error occurs while parsing the data.
     *
     * @param errorMessage - the message to be displayed.
     */
    void onErrorOccurred(String errorMessage);
}
