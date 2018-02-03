package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.SpinnerColorAdapter;
import com.tokotab.ecommerce.adapter.SpinnerUomAdapter;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.json.JsonParser;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.SpinnerColor;
import com.tokotab.ecommerce.model.SpinnerUom;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private List<SpinnerColor> spinnerColors = new ArrayList<>();
    private List<SpinnerUom> spinnerUoms = new ArrayList<>();
    private List<String> stringUom = new ArrayList<>();
    SpinnerColorAdapter spinnerColorAdapter;
    SpinnerUomAdapter spinnerUomAdapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    SharedPreferences pref;
    SharedPreferences.Editor editor2;
    private Produk produk = new Produk();
    Spinner spinnerColor;
    Spinner spinnerUom;
    TextView detailName, detailFake, detailPrice, textWarna;
    EditText qty;
    private SliderLayout sliderLayout;
    Button plus, minus, save;
    String[] color;
    private int selectedRow;
    String[] uomnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);
        editor = sharedPreferences.edit();
        pref = getApplicationContext().getSharedPreferences("cart", 0); // 0 - for private mode
        editor2 = pref.edit();

        detailName = (TextView) findViewById(R.id.detail_name);
        detailFake = (TextView) findViewById(R.id.detail_fake);
        detailPrice = (TextView) findViewById(R.id.detail_price);
        textWarna = (TextView) findViewById(R.id.txt_warna);
        plus = (Button) findViewById(R.id.btn_plus);
        minus = (Button) findViewById(R.id.btn_min);
        save = (Button) findViewById(R.id.btn_save);
        qty = (EditText) findViewById(R.id.txt_qty);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        produk.setProductName(getIntent().getStringExtra(Produk.ARG_PRODUCT_NAME));
        produk.setInternalID(getIntent().getStringExtra(Produk.ARG_INTERNAL_ID));
        produk.setProductPrice(getIntent().getStringExtra(Produk.ARG_PRODUCT_PRICE));
        produk.setFakePrice(getIntent().getStringExtra(Produk.ARG_FAKE_PRICE));
        produk.setProductPicture1(getIntent().getStringExtra(Produk.ARG_PP_1));
        produk.setProductPicture2(getIntent().getStringExtra(Produk.ARG_PP_2));
        produk.setProductPicture3(getIntent().getStringExtra(Produk.ARG_PP_3));
        produk.setProductPicture4(getIntent().getStringExtra(Produk.ARG_PP_4));
        produk.setCategoryInternalID(getIntent().getStringExtra(Produk.ARG_PRODUCT_CATEGORY));
        produk.setProductColor(getIntent().getStringExtra(Produk.ARG_PRODUCT_COLOR));
        produk.setProductDescription(getIntent().getStringExtra(Produk.ARG_PRODUCT_DESC));
        produk.setStatus(getIntent().getStringExtra(Produk.ARG_STATUS));
        produk.setProductType(getIntent().getStringExtra(Produk.ARG_PRODUCT_TYPE));
        produk.setProductID(getIntent().getStringExtra(Produk.ARG_PRODUCT_ID));
        produk.setNewProduct(getIntent().getStringExtra(Produk.ARG_PRODUCT_NEW));
        produk.setTopProduct(getIntent().getStringExtra(Produk.ARG_PRODUCT_TOP));

        DetailActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        //spinner uom
        spinnerUom = (Spinner) findViewById(R.id.satuan_spinner);
        spinnerUomAdapter = new SpinnerUomAdapter(this, R.layout.spinner_uom, spinnerUoms);
        spinnerUom.setAdapter(spinnerUomAdapter);

        //spinner color
        spinnerColor = (Spinner) findViewById(R.id.color_spinner);
        spinnerColorAdapter = new SpinnerColorAdapter(this, R.layout.spinner_color, spinnerColors);
        spinnerColor.setAdapter(spinnerColorAdapter);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = 0;
                if (!qty.getText().toString().equals("")) {
                    quant = Integer.parseInt(qty.getText().toString());
                }
                qty.setText(String.valueOf(quant + 1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = 0;
                if (!qty.getText().toString().equals("")) {
                    quant = Integer.parseInt(qty.getText().toString());
                }
                if (quant < 1) quant = 1;
                qty.setText(String.valueOf(quant - 1));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = DetailActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(!sharedPreferences.getString(User.MEMBER_TYPE, "-1").equals("-1")) {
                    int quant = 0;
                    if (!qty.getText().toString().equals("")) {
                        quant = Integer.parseInt(qty.getText().toString());
                    }
                    if (quant > 0) {
                        String UOMterpilih = stringUom.get(spinnerUom.getSelectedItemPosition());
                        String[] valueUOM = UOMterpilih.split("--,--");
                        String priceTerpilih = valueUOM[2];
                        if (!valueUOM[3].equals("no")) {
                            priceTerpilih = valueUOM[3];
                        }
                        String selectedColorInternalID = "-";
                        String selectedColorHexa = "-";
                        String selectedColorName = "-";
                        if (!produk.getProductColor().equals("-")) {
                            String[] detail_color = color[spinnerColor.getSelectedItemPosition()].split(";");
                            selectedColorInternalID = detail_color[2];
                            selectedColorHexa = detail_color[0];
                            selectedColorName = detail_color[1];
                        }
                        String previousName = pref.getString(Produk.ARG_PRODUCT_NAME, null);
                        String previousUOMID = pref.getString(SpinnerUom.ARG_INTERNAL_ID, null);
                        String previousUOMName = pref.getString(SpinnerUom.ARG_UOM_ID, null);
                        String previousID = pref.getString(Produk.ARG_INTERNAL_ID, null);
                        String previousQty = pref.getString("qty", null);
                        String previousColorInternalID = pref.getString(SpinnerColor.ARG_INTERNAL_ID, null);
                        String previousColorHexa = pref.getString(SpinnerColor.ARG_COLOR_HEXA, null);
                        String previousColorName = pref.getString(SpinnerColor.ARG_COLOR_NAME, null);
                        String previousPrice = pref.getString(Produk.ARG_PRODUCT_PRICE, null);
                        String previousPict = pref.getString(Produk.ARG_PP_1, null);
                        if (previousID != null) {
                            String[] lastID = previousID.split("-,-");
                            String[] lastIDUOM = previousUOMID.split("-,-");
                            String[] lastColor = previousColorInternalID.split("-,-");
                            if (!cekAdaID(lastID, lastIDUOM, lastColor, valueUOM[0], selectedColorInternalID)) {
                                editor2.putString(Produk.ARG_PRODUCT_NAME, previousName + "-,-" + produk.getProductName());
                                editor2.putString("qty", previousQty + "-,-" + qty.getText().toString());
                                editor2.putString(Produk.ARG_PRODUCT_PRICE, previousPrice + "-,-" + priceTerpilih);
                                editor2.putString(Produk.ARG_PP_1, previousPict + "-,-" + produk.getProductPicture1());
                                editor2.putString(Produk.ARG_INTERNAL_ID, previousID + "-,-" + produk.getInternalID());
                                editor2.putString(SpinnerUom.ARG_INTERNAL_ID, previousUOMID + "-,-" + valueUOM[0]);
                                editor2.putString(SpinnerUom.ARG_UOM_ID, previousUOMName + "-,-" + valueUOM[1]);
                                editor2.putString(SpinnerColor.ARG_INTERNAL_ID, previousColorInternalID + "-,-" + selectedColorInternalID);
                                editor2.putString(SpinnerColor.ARG_COLOR_HEXA, previousColorHexa + "-,-" + selectedColorHexa);
                                editor2.putString(SpinnerColor.ARG_COLOR_NAME, previousColorName + "-,-" + selectedColorName);
                            } else {
                                editor2.putString("qty", arrayToString());
                            }
                        } else {
                            editor2.putString(Produk.ARG_PRODUCT_NAME, produk.getProductName());
                            editor2.putString(Produk.ARG_INTERNAL_ID, produk.getInternalID());
                            editor2.putString(Produk.ARG_PP_1, produk.getProductPicture1());
                            editor2.putString("qty", qty.getText().toString());
                            editor2.putString(Produk.ARG_PRODUCT_PRICE, priceTerpilih);
                            editor2.putString(SpinnerUom.ARG_INTERNAL_ID, valueUOM[0]);
                            editor2.putString(SpinnerUom.ARG_UOM_ID, valueUOM[1]);
                            editor2.putString(SpinnerColor.ARG_INTERNAL_ID, selectedColorInternalID);
                            editor2.putString(SpinnerColor.ARG_COLOR_HEXA, selectedColorHexa);
                            editor2.putString(SpinnerColor.ARG_COLOR_NAME, selectedColorName);
                        }
                        editor2.commit();
                        Toast.makeText(v.getContext(), "Barang berhasil dimasukkan ke Keranjang Belanja", Toast.LENGTH_SHORT).show();
                    } else {
                        qty.requestFocus();
                        Toast.makeText(v.getContext(), "Isikan jumlah barang yang hendak dibeli", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(v.getContext(), "Harap login untuk melakukan pembelian", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }

            public boolean cekAdaID(String[] id, String[] uomID, String[] colorID, String selectedUOM, String selectedColor) {
                for (int i = 0; i < id.length; i++) {
                    if (id[i].equals(produk.getInternalID()) && uomID[i].equals(selectedUOM) && colorID[i].equals(selectedColor)) {
                        selectedRow = i;
                        return true;
                    }
                }
                return false;
            }

            public String arrayToString() {
                String[] tes = pref.getString("qty", null).split("-,-");
                String coba = null;
                tes[selectedRow] = String.valueOf(Integer.parseInt(tes[selectedRow]) + Integer.parseInt(qty.getText().toString()));
                for (int i = 0; i < tes.length; i++) {
                    if (coba == null) coba = tes[i];
                    else coba = coba + "-,-" + tes[i];
                }
                return coba;
            }
        });
    }

    public void setDetail() {
        detailName.setText(produk.getProductName());
        if(!produk.getFakePrice().trim().equals("0.00")) {
            detailFake.setText("Rp." + String.format("%1$,.2f", Double.parseDouble(produk.getFakePrice())));
            detailFake.setPaintFlags(detailFake.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            detailFake.setVisibility(View.GONE);
            detailPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        detailPrice.setText("Rp."+String.format("%1$,.2f",Double.parseDouble(produk.getProductPrice())));

        if (!produk.getProductColor().equals("-")) {
            color = produk.getProductColor().split("--,--");
            for (String warna : color
                    ) {
                String[] detail_color = warna.split(";");
                SpinnerColor spin = new SpinnerColor(detail_color[1], detail_color[0], detail_color[2]);
                spinnerColors.add(spin);
            }
        } else {
            textWarna.setVisibility(View.GONE);
            spinnerColor.setVisibility(View.GONE);
        }
        spinnerColorAdapter.notifyDataSetChanged();
        for (String uom : stringUom
                ) {
            uomnya = uom.split("--,--");
            String bussiness = "";
            if (!uomnya[3].equals("no")) {
                bussiness = uomnya[3];
            }
            spinnerUoms.add(new SpinnerUom(uomnya[0], uomnya[1], uomnya[2], bussiness));
        }
        spinnerUomAdapter.notifyDataSetChanged();

        for (int i = 0; i < 4; i++) {
            BaseSliderView textSliderView = new DefaultSliderView(this);
            String img = produk.getProductPicture1();
            if (i == 1) {
                img = produk.getProductPicture2();
            } else if (i == 2) {
                img = produk.getProductPicture3();
            } else if (i == 3) {
                img = produk.getProductPicture4();
            }
            img = img.replaceAll(" ", "%20");
            if (!img.equals("0")) {
                textSliderView
                        .image("http://home.stationeryone.com/uploaded_img/600x600/" + img)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                Log.d("data", slider.getUrl());
                            }
                        });
                sliderLayout.addSlider(textSliderView);
            }
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.detailmenu_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String url = "http://home.stationeryone.com/detail/" + produk.getProductID();
            url = url.replaceAll(" ", "%20");
            intent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(intent, "Share with"));
        }else if(id == R.id.detailmenu_cart){
            Intent intentCart = new Intent(DetailActivity.this, CartActivity.class);
            startActivity(intentCart);
        }
        return super.onOptionsItemSelected(item);
    }

    private class prepareTask extends AsyncTask<String, Void, List<String>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DetailActivity.this, "Please wait", "Receiving data..", true, false);
        }
        @Override
        protected List<String> doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            InputStream data = httpClient.sendJSONstream(produk.getInternalID() + "--,--" + sharedPreferences.getString(User.MEMBER_TYPE, "-1"), "http://home.stationeryone.com/getDataUom");
            JsonParser jsoNparser = new JsonParser();
            stringUom = jsoNparser.getDataUom(data);
            return stringUom;
        }

        @Override
        protected void onPostExecute(List<String> uoms) {
            super.onPostExecute(uoms);
            setDetail();//set detail
            progressDialog.dismiss();
        }
    }
}
