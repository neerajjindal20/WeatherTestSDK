package com.weather.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.weather.test.api.WeatherDetails;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by neeraj on 4/13/15.
 *
 * This Activity shows the weather details for the selected zip code.
 */
public class WeatherDetailActivity extends Activity {

    @InjectView(R.id.txt_location)
    TextView mTxtLocationName;
    @InjectView(R.id.txt_location_lat)
    TextView mTxtLocationLatitude;
    @InjectView(R.id.txt_location_long)
    TextView mTxtLocationLongitude;
    @InjectView(R.id.txt_weather_condition)
    TextView mTxtWeatherCondition;
    @InjectView(R.id.txt_weather_description)
    TextView mTxtWeatherDescription;
    @InjectView(R.id.txt_temp)
    TextView mTxtTemperature;
    @InjectView(R.id.txt_min_temp)
    TextView mTxtMinimumTemperature;
    @InjectView(R.id.txt_max_temp)
    TextView mTxtMaximumTemperature;
    @InjectView(R.id.txt_pressure)
    TextView mTxtPressure;
    @InjectView(R.id.txt_humidity)
    TextView mTxtHumidity;
    @InjectView(R.id.txt_wind_speed)
    TextView mTxtWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        ButterKnife.inject(this);

        WeatherDetails weatherDetails = (WeatherDetails) getIntent().getSerializableExtra(ZipListActivity.INTENT_TAG_WEATHER_DETAILS);

        mTxtLocationName.setText("Location : " + weatherDetails.getLocationName());
        mTxtLocationLatitude.setText("Latitude : " + weatherDetails.getLocationLatitude());
        mTxtLocationLongitude.setText("Longitude : " + weatherDetails.getLocationLongitude());
        mTxtWeatherCondition.setText("Weather Condition : " + weatherDetails.getWeatherCondition());
        mTxtWeatherDescription.setText("Description : " + weatherDetails.getWeatherDescription());
        mTxtTemperature.setText("Temperature : " + weatherDetails.getCurrentTemperature());
        mTxtMinimumTemperature.setText("Minimum Temperature : " + weatherDetails.getMinimumTemperature());
        mTxtMaximumTemperature.setText("Maximum Temperature : " + weatherDetails.getMaximumTemperature());
        mTxtPressure.setText("Pressure : " + weatherDetails.getPressure());
        mTxtHumidity.setText("Humidity : " + weatherDetails.getHumidity());
        mTxtWindSpeed.setText("Wind Speed : " + weatherDetails.getWindSpeed());
    }
}
