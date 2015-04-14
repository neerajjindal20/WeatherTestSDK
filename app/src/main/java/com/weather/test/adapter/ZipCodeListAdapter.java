package com.weather.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weather.test.R;

import java.util.List;

/**
 * Created by neeraj on 4/13/15.
 *
 * This class renders the list items to be shown in the zip code list.
 */
public class ZipCodeListAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInfater;

    public ZipCodeListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mInfater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInfater.inflate(R.layout.zip_code_list_item, null);
        TextView txtZipCode = (TextView) convertView.findViewById(R.id.txt_zip_code);
        txtZipCode.setText(getItem(position));
        return convertView;
    }
}
