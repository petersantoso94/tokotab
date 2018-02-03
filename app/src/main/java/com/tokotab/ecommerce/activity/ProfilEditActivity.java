package com.tokotab.ecommerce.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

public class ProfilEditActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView txt_nama,txt_telp,txt_alamat,txt_pass_old,txt_pass_new,txt_pass_new_rep;
    private Button btn_password;
    private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit);

        sharedPreferences = getApplicationContext().getSharedPreferences("user",0);
        editor = sharedPreferences.edit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profil);
        txt_nama = (TextView) findViewById(R.id.etName_edit_profil);
        txt_telp = (TextView) findViewById(R.id.etPhone_edit_profil);
        txt_pass_old = (TextView) findViewById(R.id.etPassword_old);
        txt_pass_new = (TextView) findViewById(R.id.etPassword);
        txt_pass_new_rep = (TextView) findViewById(R.id.etPassword_again);
        cardView = (CardView)findViewById(R.id.password_form_container);
        txt_alamat = (TextView) findViewById(R.id.etAddress_edit_profil);
        btn_password = (Button) findViewById(R.id.btn_edit_password);
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab_edit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                if(CheckNetwork.isInternetAvailable(ProfilEditActivity.this)) //returns true if internet available
                                {
                                    String edit = sharedPreferences.getString(User.INTERNAL_ID,"-")+"-,-"+txt_nama.getText().toString()+"-,-"+txt_alamat.getText().toString()+"-,-"+txt_telp.getText().toString();
                                    new SendDataToServer().execute(edit);
                                }else
                                {
                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which){
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

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfilEditActivity.this);
                                    builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("ya", dialogClickListener)
                                            .setNegativeButton("tidak", dialogClickListener).show();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilEditActivity.this);
                builder.setMessage("Apakah anda yakin mengubah profil anda?").setPositiveButton("ya", dialogClickListener)
                        .setNegativeButton("tidak", dialogClickListener).show();
            }
        });
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Edit Profil");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_nama.setText(sharedPreferences.getString(User.MEMBER_NAME,"-"));
        txt_alamat.setText(sharedPreferences.getString(User.MEMBER_ADDRESS,"-"));
        txt_telp.setText(sharedPreferences.getString(User.MEMBER_PHONE,"-"));
    }
    public void afterSending() {
        editor.putString(User.MEMBER_NAME,txt_nama.getText().toString());
        editor.putString(User.MEMBER_ADDRESS,txt_alamat.getText().toString());
        editor.putString(User.MEMBER_PHONE,txt_telp.getText().toString());
        editor.commit();
        if(!txt_pass_old.getText().toString().equals("")){
            if (sharedPreferences.getString(User.MEMBER_PASS, "-").equals(txt_pass_old.getText().toString())) {
                if(txt_pass_new_rep.getText().toString().equals(txt_pass_new.getText().toString())) {
                    String sent = sharedPreferences.getString(User.INTERNAL_ID, "-") + "--,--" + txt_pass_new.getText().toString();
                    new SendDataToServerPassword().execute(sent);
                }else{
                    txt_pass_new_rep.requestFocus();
                    Toast.makeText(getApplicationContext(),"Ulangi Password tidak sama!",Toast.LENGTH_SHORT).show();
                }
            }else {
                txt_pass_old.requestFocus();
                Toast.makeText(getApplicationContext(),"Password Lama tidak sesuai!",Toast.LENGTH_SHORT).show();
            }
        }else {
            Intent i = new Intent(ProfilEditActivity.this, ProfilActivity.class);
            startActivity(i);
        }
    }

    public void lastPost(){
        editor.putString(User.MEMBER_PASS,txt_pass_new.getText().toString());
        editor.commit();
        Intent i = new Intent(ProfilEditActivity.this, ProfilActivity.class);
        startActivity(i);
    }
    private class SendDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            String response = httpClient.sendJSON(params[0], "http://home.stationeryone.com/editProfileAndroid");
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            afterSending();
        }
    }
    private class SendDataToServerPassword extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            String response = httpClient.sendJSON(params[0], "http://home.stationeryone.com/setPasswordAndroid");
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            lastPost();
        }
    }
}
