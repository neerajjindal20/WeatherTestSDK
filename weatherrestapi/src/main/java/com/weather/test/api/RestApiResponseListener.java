package com.weather.test.api;

/**
 * Created by neeraj on 4/13/15.
 *
 * This handles the data fetched from the server.
 */
public interface RestApiResponseListener {

    /**
     * This is called when the data has been fetched successfully.
     *
     * @param response - the server response.
     */
    void onResponseReceived(String response);

    /**
     * This is called if an error occurs while fetching the data.
     *
     * @param errorMessage - the message to be displayed.
     */
    void onError(String errorMessage);
}
