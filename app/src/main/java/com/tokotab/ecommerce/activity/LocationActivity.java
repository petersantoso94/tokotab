package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.json.JsonParser;
import com.tokotab.ecommerce.model.City;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.Province;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private Spinner provinsi, kota;
    private TextView grandTotal, diskon, shipping;
    private Double shippingCost,grandTotalCost;
    Button btn_save_loc;
    EditText Destination;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_location);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tujuan Pengiriman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init
        provinsi = (Spinner) findViewById(R.id.provinsi_spinner);
        kota = (Spinner) findViewById(R.id.kota_spinner);
        btn_save_loc = (Button) findViewById(R.id.btn_save_location);
        Destination = (EditText) findViewById(R.id.txt_alamat_pengiriman);
        sharedPreferences = getApplicationContext().getSharedPreferences("cart", 0);
        editor = sharedPreferences.edit();
        grandTotal = (TextView) findViewById(R.id.grandtotal_location);
        diskon = (TextView) findViewById(R.id.diskon_location);
        shipping = (TextView) findViewById(R.id.shipping_location);

        //task
        if (CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            prepareTask a = new prepareTask();
            a.execute();
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        //action
        provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer count = 0;
                for (City abc : cityList
                        ) {
                    if (abc.getProvinceInternalID().toString().equals(provinceList.get(position).getInternalID().toString())) {
                        count++;
                    }
                }
                String[] kotal = new String[count];
                count = 0;
                for (City abc : cityList
                        ) {
                    if (abc.getProvinceInternalID().toString().equals(provinceList.get(position).getInternalID().toString())) {
                        kotal[count] = abc.getCityName();
                        count++;
                    }
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(LocationActivity.this, android.R.layout.simple_spinner_item, kotal);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                kota.setAdapter(adapter2);
                Double multiplier = Double.parseDouble(provinceList.get(position).getMultiplier());
                Double shippingbefore = Double.parseDouble(sharedPreferences.getString(Transaksi.ARG_SHIPPING,"0"));
                shippingCost = multiplier * shippingbefore;
                grandTotalCost = Double.parseDouble(sharedPreferences.getString(Transaksi.ARG_GRAND_TOTAL,"0")) + ((multiplier-1) * shippingbefore);
                shipping.setText("Rp."+String.format("%1$,.2f",shippingCost));
                grandTotal.setText("Rp."+String.format("%1$,.2f",grandTotalCost));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_save_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Destination.getText().toString().equals("")) {
                    Intent i = new Intent(LocationActivity.this, PurchaseMethod.class);
                    editor.putString(Transaksi.ARG_ALAMAT, Destination.getText().toString());
                    editor.putString(Transaksi.ARG_KOTA, kota.getSelectedItem().toString());
                    editor.putString(Transaksi.ARG_PROVINSI, provinsi.getSelectedItem().toString());
                    editor.putString(City.ARG_INTERNAL_ID, cityList.get(provinsi.getSelectedItemPosition()).getInternalID().toString());
                    editor.putString(Province.ARG_INTERNAL_ID, provinceList.get(provinsi.getSelectedItemPosition()).getInternalID().toString());
                    editor.putString(Transaksi.ARG_SHIPPING, shippingCost.toString());
                    editor.putString(Transaksi.ARG_GRAND_TOTAL, grandTotalCost.toString());
                    editor.commit();
                    startActivity(i);
                } else {
                    Toast.makeText(LocationActivity.this, "Isikan alamat tujuan", Toast.LENGTH_SHORT).show();
                    Destination.requestFocus();
                }
            }
        });
    }

    private class prepareTask extends AsyncTask<String, Void, List<Province>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LocationActivity.this, "Please wait", "Receiving data..", true, false);
        }

        @Override
        protected List<Province> doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            InputStream data = httpClient.getData("http://home.stationeryone.com/getDataCity");
            InputStream data2 = httpClient.getData("http://home.stationeryone.com/getDataProvince");
            JsonParser jsoNparser = new JsonParser();
            provinceList = jsoNparser.getDataProvince(data2);
            cityList = jsoNparser.getDataCity(data);
            return provinceList;
        }

        @Override
        protected void onPostExecute(List<Province> produks) {
            super.onPostExecute(produks);
            //filling spinner
            String[] provlist = new String[provinceList.size()], cityl = new String[cityList.size()];
            Integer i = 0;
            for (Province list : provinceList
                    ) {
                provlist[i] = list.getProvinceName();
                i++;
            }
            Integer count = 0;
            for (City abc : cityList
                    ) {
                if (abc.getProvinceInternalID().toString().equals(provinceList.get(0).getInternalID().toString())) {
                    count++;
                }
            }
            String[] kotal = new String[count];
            count = 0;
            for (City abc : cityList
                    ) {
                if (abc.getProvinceInternalID().toString().equals(provinceList.get(0).getInternalID().toString())) {
                    kotal[count] = abc.getCityName();
                    count++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocationActivity.this, android.R.layout.simple_spinner_item, provlist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provinsi.setAdapter(adapter);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(LocationActivity.this, android.R.layout.simple_spinner_item, kotal);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            kota.setAdapter(adapter2);

            Double multiplier = Double.parseDouble(provinceList.get(0).getMultiplier());
            Double shippingbefore = Double.parseDouble(sharedPreferences.getString(Transaksi.ARG_SHIPPING,"0"));
            shippingCost = multiplier * shippingbefore;
            grandTotalCost = Double.parseDouble(sharedPreferences.getString(Transaksi.ARG_GRAND_TOTAL,"0")) + ((multiplier-1) * shippingbefore);
            shipping.setText("Rp."+String.format("%1$,.2f",shippingCost));
            grandTotal.setText("Rp."+String.format("%1$,.2f",grandTotalCost));
            diskon.setText("Rp."+String.format("%1$,.2f",Double.parseDouble(sharedPreferences.getString(Transaksi.ARG_DISKON,"0"))));
            progressDialog.dismiss();
        }
    }
}
