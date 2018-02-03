package com.tokotab.ecommerce.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.User;

public class AfterPurchase extends AppCompatActivity {
    private Button btn_back;
    private TextView txt_ty;
    private SharedPreferences sharedPreferences,pref;
    SharedPreferences.Editor editor2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_purchase);

        btn_back = (Button) findViewById(R.id.btn_back_to_shop);
        pref = getApplicationContext().getSharedPreferences("cart", 0);
        editor2 = pref.edit();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor2.clear();
                editor2.commit();
                Intent i = new Intent(AfterPurchase.this,MainActivity.class);
                startActivity(i);
            }
        });
        txt_ty = (TextView) findViewById(R.id.txt_terima_kasih);
        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);

        txt_ty.setText("Pastikan anda memasukan nomor transaksi "+pref.getString(Transaksi.ARG_SALES_HEADER,"")+"pada kolom berita transfer anda. \nPembayaran transaksi paling lambat 2 jam setelah pemesanan. \nSilahkan cek email anda ("+sharedPreferences.getString(User.MEMBER_EMAIL," ")+") untuk melihat detail pembelian anda.");
    }
}
