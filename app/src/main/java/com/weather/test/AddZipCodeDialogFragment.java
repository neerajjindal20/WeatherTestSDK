package com.weather.test;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by neeraj on 4/14/15.
 *
 * This dialog fragment is used to add new zip codes in the app.
 */
public class AddZipCodeDialogFragment extends DialogFragment {

    @InjectView(R.id.input_zip_code)
    EditText mInputZipCode;
    @InjectView(R.id.btn_cancel)
    Button mBtnCancel;
    @InjectView(R.id.btn_add)
    Button mBtnAdd;

    private OnAddActionListener mActionListener;

    public static AddZipCodeDialogFragment newInstance(OnAddActionListener actionListener) {
        AddZipCodeDialogFragment frag = new AddZipCodeDialogFragment();
        frag.mActionListener = actionListener;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setTitle(R.string.add_zip_code);
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        return d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_zip_code, null);
        ButterKnife.inject(this, v);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.onAddPressed(mInputZipCode.getText().toString());
                dismiss();
            }
        });
        return v;
    }
}
