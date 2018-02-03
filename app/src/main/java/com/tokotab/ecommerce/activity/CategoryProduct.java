package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.ProductAllAdapter;
import com.tokotab.ecommerce.adapter.ProductCategoryAdapter;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.json.JsonParser;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryProduct extends AppCompatActivity {
    private List<Produk> produkListAll = new ArrayList<>();
    private List<Produk> produkListAllTemp = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private String internal;
    private ProductCategoryAdapter productAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        String nama=null;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            internal = null;
        }else {
            nama = extras.getString("nama");
            internal = extras.getString("internalID");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_all_product);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreferences = getApplicationContext().getSharedPreferences("user",0);
        editor = sharedPreferences.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_all_product);

        Log.d("internalID",internal);

        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            prepareTask a = new prepareTask();
            a.execute();
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
            builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        searchView = (SearchView) findViewById(R.id.search_category_product);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search));

        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(ContextCompat.getColor(this,R.color.cardview_light_background));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                produkListAll.clear();
                produkListAll.addAll(produkListAllTemp);
                productAdapter.notifyDataSetChanged();
                List<Produk> filteredModelList = filter(produkListAll, query);
                productAdapter.animateTo(filteredModelList);
                recyclerView.scrollToPosition(0);
                return true;
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item .getItemId();
        Intent intentCart;

        switch (i) {
            case R.id.cart :
                intentCart = new Intent(CategoryProduct.this, CartActivity.class);
                startActivity(intentCart);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Produk> filter(List<Produk> models, String query) {
        query = query.toLowerCase();
        List<Produk> filteredModelList = new ArrayList<>();
        for (Produk model : models) {
            final String text = model.getProductName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private class prepareTask extends AsyncTask<String,Void,List<Produk>> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CategoryProduct.this, "Please wait", "Receiving data..", true, false);
        }
        @Override
        protected List<Produk> doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            InputStream dataAll = httpClient.sendJSONstream("kategori--,--"+internal+"--,--"+sharedPreferences.getString(User.MEMBER_TYPE,"-1"),"http://home.stationeryone.com/getDataProduct");
            JsonParser jsoNparser = new JsonParser();
            produkListAll = jsoNparser.getData(dataAll);
            return  produkListAll;
        }
        @Override
        protected void onPostExecute(List<Produk> produks) {
            super.onPostExecute(produks);
            progressDialog.dismiss();
            produkListAllTemp = new ArrayList<>(produks);
            productAdapter = new ProductCategoryAdapter(produkListAll,getApplicationContext());
            productAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(productAdapter);
        }
    }
}
