package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.CartAdapter;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.Province;
import com.tokotab.ecommerce.model.SpinnerColor;
import com.tokotab.ecommerce.model.SpinnerUom;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView grandTotal;
    private SharedPreferences sharedPreferences,pref;
    SharedPreferences.Editor editor2;
    CartAdapter cartAdapter;
    Button btn_buy;
    private String[] ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName,
            ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture;
    private String CustomerInternalID, CustomerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        grandTotal = (TextView) findViewById(R.id.grandtotal);
        btn_buy = (Button) findViewById(R.id.btn_save_cart);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.cart));

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
                if (ProdukInternalID.length >0) {
                    Intent i = new Intent(CartActivity.this,CartDetailActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Keranjang kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cartAdapter = new CartAdapter(grandTotal, ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName, ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_cart);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CartActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);
    }
}
