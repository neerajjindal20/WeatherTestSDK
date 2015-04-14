package com.weather.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.weather.test.adapter.ZipCodeListAdapter;
import com.weather.test.api.OnWeatherProcessListener;
import com.weather.test.api.WeatherApi;
import com.weather.test.api.WeatherDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * This Activity shows the list of all the zip codes that are already added in the app.
 */
public class ZipListActivity extends Activity implements OnWeatherProcessListener, OnAddActionListener {

    public static final String INTENT_TAG_WEATHER_DETAILS = "com.weather.test.INTENT_TAG_WEATHER_DETAILS";
    public static final String ADD_ZIP_FRAGMENT_TAG = "com.weather.test.ADD_ZIP_FRAGMENT_TAG";
    public static final String PREFERENCE_KEY_ZIP_CODES = "com.weather.test.PREFERENCE_KEY_ZIP_CODES";
    public static final String mInitializationJsonString = "{\"zipcodes\":[\"14450\",\"14623\",\"94086\"]}";

    @InjectView(R.id.zip_code_list)
    ListView mListZipCodes;

    private ArrayList<String> mZipCodes = new ArrayList<String>();
    private SharedPreferences mSharedPreferences;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_list);
        ButterKnife.inject(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");

        mSharedPreferences = getSharedPreferences(getApplicationInfo().packageName, Context.MODE_PRIVATE);

        String zipCodeJson = mSharedPreferences.getString(PREFERENCE_KEY_ZIP_CODES, null);
        if (zipCodeJson == null) {
            mSharedPreferences.edit().putString(PREFERENCE_KEY_ZIP_CODES, mInitializationJsonString).commit();
        }

        populateZipCodeList();

        setListAdapter();

        registerForContextMenu(mListZipCodes);

        mListZipCodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getWeatherDetails(mZipCodes.get(position));
            }
        });
    }

    /**
     * This function calls the weather API to fetch the weather details for the selected zip code.
     *
     * @param zipCode - the selected zip code.
     */
    private void getWeatherDetails(String zipCode) {
        mProgressDialog.show();
        new WeatherApi(ZipListActivity.this).getWeatherDetailsForZip(zipCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zip_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                showAddZipCodeDialog();
                break;
            case R.id.action_delete:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.zip_code_list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Delete " + mZipCodes.get(info.position) + "?");
            menu.add("Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            mZipCodes.remove(info.position);
            saveZipCodeList();
            setListAdapter();
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Shows the add zip code dialog.
     */
    private void showAddZipCodeDialog() {
        AddZipCodeDialogFragment.newInstance(this).show(getFragmentManager(), ADD_ZIP_FRAGMENT_TAG);
    }

    /**
     * This function sets the adapter on the list based on the items contained in the arraylist.
     */
    private void setListAdapter() {
        if (mListZipCodes.getAdapter() == null) {
            mListZipCodes.setAdapter(new ZipCodeListAdapter(this, R.layout.zip_code_list_item, mZipCodes));
        } else {
            ((ZipCodeListAdapter) mListZipCodes.getAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * This function populates the arraylist of zip codes from the saved preferences.
     */
    private void populateZipCodeList() {
        String zipCodeJson = mSharedPreferences.getString(PREFERENCE_KEY_ZIP_CODES, null);
        if (zipCodeJson != null) {
            try {
                JSONObject zipCodeObject = new JSONObject(zipCodeJson);
                JSONArray zipCodeArray = zipCodeObject.getJSONArray("zipcodes");
                for (int i = 0; i < zipCodeArray.length(); i++) {
                    mZipCodes.add(zipCodeArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This function writes the arraylist of zip codes to the preferences.
     */
    private void saveZipCodeList() {
        mSharedPreferences.edit().putString(PREFERENCE_KEY_ZIP_CODES, getZipCodeListAsJson()).commit();
    }

    /**
     * This function converts the arraylist of zip codes to a json string.
     *
     * @return The json string for the list
     */
    public String getZipCodeListAsJson() {
        JSONObject zipCodeObject = new JSONObject();
        JSONArray zipCodesArray = new JSONArray();
        for (String zipCode : mZipCodes) {
            zipCodesArray.put(zipCode);
        }
        try {
            zipCodeObject.put("zipcodes", zipCodesArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return zipCodeObject.toString();
    }

    @Override
    public void onWeatherDetailsReceived(WeatherDetails weatherDetails) {
        mProgressDialog.dismiss();
        Intent intent = new Intent(this, WeatherDetailActivity.class);
        intent.putExtra(INTENT_TAG_WEATHER_DETAILS, weatherDetails);
        startActivity(intent);
    }

    @Override
    public void onErrorOccurred(String errorMessage) {
        mProgressDialog.dismiss();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddPressed(String zipCode) {
        mZipCodes.add(zipCode);
        saveZipCodeList();
        setListAdapter();
        getWeatherDetails(zipCode);
    }
}
