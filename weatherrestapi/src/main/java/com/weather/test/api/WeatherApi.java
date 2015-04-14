package com.weather.test.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neeraj on 4/13/15.
 *
 * This class triggers the rest api call to fetch the data and parses the data to encapsulate it in an object.
 */
public class WeatherApi implements RestApiResponseListener {

    private OnWeatherProcessListener mProcessListener;

    public WeatherApi(OnWeatherProcessListener processListener) {
        this.mProcessListener = processListener;
    }

    /**
     * Triggers the rest api call for the specified zip code.
     *
     * @param zipCode - the selected zip code.
     */
    public void getWeatherDetailsForZip(String zipCode) {
        new GetWeatherDetailsTask(this).execute(getWeatherUrl(zipCode));
    }

    /**
     * Appends the zip code into the weather api url.
     *
     * @param zipCode - the selected zip code.
     *
     * @return the url with the zip code.
     */
    private String getWeatherUrl(String zipCode) {
        return "http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + "&units=imperial";
    }

    @Override
    public void onResponseReceived(String response) {
        try {
            JSONObject weatherObject = new JSONObject(response);
            WeatherDetails weatherDetails = new WeatherDetails();
            JSONObject latLongObject = weatherObject.getJSONObject("coord");
            weatherDetails.setLocationLatitude(latLongObject.optDouble("lat"));
            weatherDetails.setLocationLongitude(latLongObject.optDouble("lon"));
            JSONArray weatherArray = weatherObject.optJSONArray("weather");
            if (weatherArray != null) {
                JSONObject conditionObject = weatherArray.getJSONObject(0);
                weatherDetails.setWeatherCondition(conditionObject.optString("main"));
                weatherDetails.setWeatherDescription(conditionObject.optString("description"));
            }
            JSONObject mainObject = weatherObject.getJSONObject("main");
            weatherDetails.setCurrentTemperature(mainObject.optInt("temp"));
            weatherDetails.setMinimumTemperature(mainObject.optInt("temp_min"));
            weatherDetails.setMaximumTemperature(mainObject.optInt("temp_max"));
            weatherDetails.setPressure(mainObject.optInt("pressure"));
            weatherDetails.setHumidity(mainObject.optInt("humidity"));
            JSONObject windObject = weatherObject.getJSONObject("wind");
            weatherDetails.setWindSpeed(windObject.optInt("speed"));
            weatherDetails.setLocationName(weatherObject.optString("name"));
            mProcessListener.onWeatherDetailsReceived(weatherDetails);
        } catch (JSONException e) {
            e.printStackTrace();
            onError(e.getMessage());
        }
    }

    @Override
    public void onError(String errorMessage) {
        mProcessListener.onErrorOccurred(errorMessage);
    }
}
