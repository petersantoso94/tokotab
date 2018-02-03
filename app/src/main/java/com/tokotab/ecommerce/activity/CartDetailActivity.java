package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.CartAdapter;
import com.tokotab.ecommerce.adapter.CartDetailAdapter;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.SpinnerColor;
import com.tokotab.ecommerce.model.SpinnerUom;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

public class CartDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView grandTotal, diskon, shipping;
    private SharedPreferences sharedPreferences, pref;
    SharedPreferences.Editor editor2;
    CartDetailAdapter cartAdapter;
    Button btn_buy;
    private String[] ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName,
            ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture;
    private String CustomerInternalID, CustomerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cart_detail);
        grandTotal = (TextView) findViewById(R.id.grandtotal_detail);
        diskon = (TextView) findViewById(R.id.diskon_detail);
        shipping = (TextView) findViewById(R.id.shipping_detail);
        btn_buy = (Button) findViewById(R.id.btn_save_cart_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Konfirmasi Belanjaan");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pref = getApplicationContext().getSharedPreferences("cart", 0); // 0 - for private mode
        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);
        editor2 = pref.edit();

        String previousName = pref.getString(Produk.ARG_PRODUCT_NAME, "-,-");
        String previousUOMID = pref.getString(SpinnerUom.ARG_INTERNAL_ID, "-,-");
        String previousUOMName = pref.getString(SpinnerUom.ARG_UOM_ID, "-,-");
        String previousID = pref.getString(Produk.ARG_INTERNAL_ID, "-,-");
        String previousQty = pref.getString("qty", "-,-");
        String previousColorInternalID = pref.getString(SpinnerColor.ARG_INTERNAL_ID, "-,-");
        String previousColorHexa = pref.getString(SpinnerColor.ARG_COLOR_HEXA, "-,-");
        String previousColorName = pref.getString(SpinnerColor.ARG_COLOR_NAME, "-,-");
        String previousPrice = pref.getString(Produk.ARG_PRODUCT_PRICE, "-,-");
        String previousPict = pref.getString(Produk.ARG_PP_1, "-,-");

        ProdukInternalID = previousID.split("-,-");
        UOMInternalID = previousUOMID.split("-,-");
        ColorInternalID = previousColorInternalID.split("-,-");
        Qty = previousQty.split("-,-");
        CustomerInternalID = sharedPreferences.getString(User.INTERNAL_ID, "-1");
        CustomerEmail = sharedPreferences.getString(User.MEMBER_EMAIL, "-1");
        ProdukName = previousName.split("-,-");
        ProdukPrice = previousPrice.split("-,-");
        ColorName = previousColorName.split("-,-");
        ColorHexa = previousColorHexa.split("-,-");
        UOMName = previousUOMName.split("-,-");
        ProdukPicture = previousPict.split("-,-");

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartDetailActivity.this, LocationActivity.class);
                startActivity(i);
            }
        });
        //task
        String objek = "";
        for (int i = 0; i < ProdukInternalID.length; i++) {
            if (i == 0)
                objek = ProdukInternalID[i] + ";" + UOMInternalID[i] + ";" + ColorInternalID[i] + ";" + Qty[i];
            else
                objek += "--,--" + ProdukInternalID[i] + ";" + UOMInternalID[i] + ";" + ColorInternalID[i] + ";" + Qty[i];
        }
        objek += "--`--" + CustomerInternalID;
        if (!objek.equals("")) {
            if (CheckNetwork.isInternetAvailable(getApplicationContext())) //returns true if internet available
            {
                new SendDataToServer().execute(objek);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("ya", dialogClickListener)
                        .setNegativeButton("tidak", dialogClickListener).show();
            }
        }
    }

    private class SendDataToServer extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CartDetailActivity.this, "Please wait", "Receiving data..", true, false);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            String response = httpClient.sendJSON(params[0], "http://home.stationeryone.com/requestGrandTotal");
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            response = response.trim();
            String[] isi = response.split("--,--");
            editor2.putString(Transaksi.ARG_GRAND_TOTAL, isi[0]);
            editor2.putString(Transaksi.ARG_DISKON, isi[1]);
            editor2.putString(Transaksi.ARG_SHIPPING, isi[2]);
            editor2.commit();
            if (!isi[0].equals("0"))
                grandTotal.setText("Rp." + String.format("%1$,.2f",Double.parseDouble(isi[0])) );
            else
                grandTotal.setText("Rp.0,-");
            if (!isi[1].equals("0"))
                diskon.setText("Rp." + String.format("%1$,.2f",Double.parseDouble(isi[1])) );
            else
                diskon.setText("Rp.0,-");
            if (!isi[2].equals("0"))
                shipping.setText("Rp." + String.format("%1$,.2f",Double.parseDouble(isi[2])));
            else
                shipping.setText("Rp.0,-");
            cartAdapter = new CartDetailAdapter(grandTotal, ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName, ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cart_detail);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(CartDetailActivity.this, 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(cartAdapter);
            progressDialog.dismiss();
        }
    }
}
