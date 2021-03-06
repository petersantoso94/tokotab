package com.tokotab.ecommerce.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.SpinnerColor;

import java.util.ArrayList;
import java.util.List;


public class SpinnerColorAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private List data;
    SpinnerColor spinnerColor = null;
    LayoutInflater inflater;

    /*************
     * CustomAdapter Constructor
     *****************/
    public SpinnerColorAdapter(
            Activity activitySpinner,
            int textViewResourceId,
            List objects
    ) {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_color, parent, false);

        /***** Get each Model object from Arraylist ********/
        spinnerColor = (SpinnerColor) data.get(position);

        View colorSpinner = row.findViewById(R.id.color_spinner);
        TextView textSpinner = (TextView) row.findViewById(R.id.color_text);

        // Set values for spinner each row
        colorSpinner.setBackgroundColor(Color.parseColor(spinnerColor.getColor()));
        textSpinner.setText(spinnerColor.getName());

        return row;
    }
}
