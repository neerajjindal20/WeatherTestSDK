package com.weather.test.api;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by neeraj on 4/13/15.
 *
 * This is an async task that makes a rest api call to fetch the data for the specified url.
 */
public class GetWeatherDetailsTask extends AsyncTask<String, Void, String> {

    private RestApiResponseListener mResponseListener;

    public GetWeatherDetailsTask(RestApiResponseListener responseListener) {
        this.mResponseListener = responseListener;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(params[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                mResponseListener.onError(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            mResponseListener.onError(e.getMessage());
        } catch (IOException e) {
            mResponseListener.onError(e.getMessage());
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String response) {
        if(response != null){
            mResponseListener.onResponseReceived(response);
        }
    }
}
