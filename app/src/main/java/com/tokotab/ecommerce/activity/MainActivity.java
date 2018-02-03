package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tokotab.ecommerce.R;

import com.tokotab.ecommerce.fragments.BerandaFragment;
import com.tokotab.ecommerce.fragments.KategoriFragment;
import com.tokotab.ecommerce.fragments.ViewPagerAdapter;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.json.JsonParser;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.SliderImage;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences, preftrans, prefcart;
    private SharedPreferences.Editor editor, editor1, editor2;
    ViewPagerAdapter adapter;
    private List<Produk> produkListTerbaru = new ArrayList<>();
    private List<ListCategory> listCategories = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel2 = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel3 = new ArrayList<>();
    private List<SliderImage> sliderImages = new ArrayList<>();
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);
        preftrans = getApplicationContext().getSharedPreferences("trans", 0);
        prefcart = getApplicationContext().getSharedPreferences("cart", 0);
        editor = sharedPreferences.edit();
        editor1 = preftrans.edit();
        editor2 = prefcart.edit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (!sharedPreferences.getString(User.INTERNAL_ID, "-1").equals("-1")) {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_daftar).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_profile).setVisible(true);
            View headerLayout = navigationView.getHeaderView(0);
            TextView username = (TextView) headerLayout.findViewById(R.id.username);
            username.setText(sharedPreferences.getString(User.MEMBER_NAME, "-"));
            TextView email = (TextView) headerLayout.findViewById(R.id.useremail);
            email.setText(sharedPreferences.getString(User.MEMBER_EMAIL, "-"));
        } else {
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_daftar).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
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
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            prepareTask a = new prepareTask();
            a.execute();
        }
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
//        final SearchView searchView =(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                searchView.setQuery("", false);
//                searchView.setIconified(true);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        Intent intentCart;

        switch (i) {
            case R.id.cart:
                intentCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intentCart);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BerandaFragment(sliderImages, produkListTerbaru), "BERANDA");
        adapter.addFragment(new KategoriFragment(listCategories, listCategoriesLevel2, listCategoriesLevel3), "KATEGORI");
        viewPager.setAdapter(adapter);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent i;

        if (id == R.id.nav_belanja) {

            i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_daftar) {

            i = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_login) {

            i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_cart) {
            i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_profile) {
            i = new Intent(MainActivity.this, ProfilActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            editor.clear();
                            editor1.clear();
                            editor2.clear();
                            editor.commit();
                            editor1.commit();
                            editor2.commit();
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Apakah anda yakin untuk melanjutkan pembelian?").setPositiveButton("ya", dialogClickListener)
                    .setNegativeButton("tidak", dialogClickListener).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class prepareTask extends AsyncTask<String, Void, List<Produk>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Receiving data..", true, false);
        }

        @Override
        protected List<Produk> doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            //terbaru, top, kategori--,--level--,--customer
            InputStream data = httpClient.sendJSONstream("terbaru--,--a--,--" + sharedPreferences.getString(User.MEMBER_TYPE, "-1"), "http://home.stationeryone.com/getDataProduct");
            InputStream data2 = httpClient.getData("http://home.stationeryone.com/getDataSlider");
            InputStream data3 = httpClient.sendJSONstream("1", "http://home.stationeryone.com/getDataCategory");
            InputStream data4 = httpClient.sendJSONstream("2", "http://home.stationeryone.com/getDataCategory");
            InputStream data5 = httpClient.sendJSONstream("3", "http://home.stationeryone.com/getDataCategory");
            JsonParser jsoNparser = new JsonParser();
            produkListTerbaru = jsoNparser.getData(data);
            sliderImages = jsoNparser.getDataSlider(data2);
            listCategories = jsoNparser.getDataCategory(data3);
            listCategoriesLevel2 = jsoNparser.getDataCategory(data4);
            listCategoriesLevel3 = jsoNparser.getDataCategory(data5);
            return produkListTerbaru;
        }

        @Override
        protected void onPostExecute(List<Produk> produks) {
            super.onPostExecute(produks);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            progressDialog.dismiss();
        }
    }
}
