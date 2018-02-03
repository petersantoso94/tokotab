package com.tokotab.ecommerce.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.ProfilPagerAdapter;
import com.tokotab.ecommerce.fragments.ProfilFragment;
import com.tokotab.ecommerce.fragments.RootTransaksiFragment;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.json.JsonParser;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.TransaksiDetail;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;

public class ProfilActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ProfilPagerAdapter adapter;
    FloatingActionButton fab;
    private TextView txt_username;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Transaksi> transaksiList;
    private List<TransaksiDetail> transaksiListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profil);
        txt_username = (TextView) findViewById(R.id.profil_username);
        sharedPreferences = getApplicationContext().getSharedPreferences("user",0);
        editor = sharedPreferences.edit();
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_username.setText(sharedPreferences.getString(User.MEMBER_NAME," "));
        tabLayout = (TabLayout) findViewById(R.id.tab_profil);
        viewPager = (ViewPager) findViewById(R.id.profil_viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab_edit_profil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilActivity.this, ProfilEditActivity.class);
                startActivity(i);
            }
        });

        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            String objek = sharedPreferences.getString(User.INTERNAL_ID,"-1");
            new SendDataToServer().execute(objek);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("ya", dialogClickListener)
                    .setNegativeButton("tidak", dialogClickListener).show();
        }



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    fab.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    fab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin ingin keluar dari aplikasi").setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener).show();
    }
    private class SendDataToServer extends AsyncTask<String,Void,List<Transaksi>> {
        @Override
        protected List<Transaksi> doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            InputStream data3 = httpClient.sendJSONstream(params[0],"http://home.stationeryone.com/getDataTransactionAndroid");
            InputStream data2 = httpClient.sendJSONstream(params[0],"http://home.stationeryone.com/getDetailTransactionAndroid");
            JsonParser jsoNparser = new JsonParser();
            transaksiList = jsoNparser.getDataTransaksi(data3);
            transaksiListDetail = jsoNparser.getDataTransaksiDetail(data2);
            return transaksiList;
        }

        @Override
        protected void onPostExecute(List<Transaksi> list) {
            super.onPostExecute(list);
            setViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setViewPager(ViewPager viewPager) {

        FragmentManager fm = getSupportFragmentManager();

        adapter = new ProfilPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfilFragment(), "PROFIL");
        adapter.addFragment(new RootTransaksiFragment(fm,transaksiList,transaksiListDetail), "TRANSAKSI");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;

        if (item.getItemId() == R.id.cart) {
            i = new Intent(ProfilActivity.this, CartActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
