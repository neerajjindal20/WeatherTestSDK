package com.weather.test;

/**
 * Created by neeraj on 4/14/15.
 *
 * This is used for actions performed while adding a zip in the app.
 */
public interface OnAddActionListener {

    /**
     * This is called when the zip has to be added in the app.
     *
     * @param zipCode - the zip code to be added.
     */
    void onAddPressed(String zipCode);
}
