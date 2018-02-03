package com.tokotab.ecommerce.adapter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.SpinnerColor;
import com.tokotab.ecommerce.model.SpinnerUom;
import com.tokotab.ecommerce.model.Transaksi;

import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Created by DIDLPSANTOSO on 5/4/2016.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    public String[] ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName, ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture;
    public TextView grandTotal;

    public CartAdapter(TextView grandTotal, String[] produkInternalID, String[] UOMInternalID,
                       String[] colorInternalID, String[] qty, String[] produkName,
                       String[] produkPrice, String[] colorName, String[] colorHexa, String[] UOMName, String[] produkPicture) {
        ProdukInternalID = produkInternalID;
        this.UOMInternalID = UOMInternalID;
        ColorInternalID = colorInternalID;
        Qty = qty;
        ProdukName = produkName;
        ProdukPrice = produkPrice;
        ColorName = colorName;
        ColorHexa = colorHexa;
        this.UOMName = UOMName;
        ProdukPicture = produkPicture;
        this.grandTotal = grandTotal;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice, ColorName, UOM;
        public EditText Qty;
        public LinearLayout groupColor;
        public ImageView ProductPict, delete;
        public View ColorHexa;
        public Button min, plus;

        public MyViewHolder(View itemView) {
            super(itemView);
            ProductName = (TextView) itemView.findViewById(R.id.Cart_Product_Name);
            ProductPrice = (TextView) itemView.findViewById(R.id.cart_price);
            ColorName = (TextView) itemView.findViewById(R.id.cart_color_text);
            UOM = (TextView) itemView.findViewById(R.id.cart_uom);
            Qty = (EditText) itemView.findViewById(R.id.cart_qty);
            groupColor = (LinearLayout) itemView.findViewById(R.id.cart_color_group);
            ProductPict = (ImageView) itemView.findViewById(R.id.cart_image);
            ColorHexa = (View) itemView.findViewById(R.id.cart_color);
            min = (Button) itemView.findViewById(R.id.btn_cart_min);
            plus = (Button) itemView.findViewById(R.id.btn_cart_plus);
            delete = (ImageView) itemView.findViewById(R.id.btn_delete_cart);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_konten, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MyViewHolder a = holder;
        final int pos = position;
        holder.ProductName.setText(ProdukName[position]);
        setImage(holder.ProductPict, ProdukPicture[position]);
        holder.ColorName.setText(ColorName[position]);
        holder.UOM.setText(UOMName[position]);
        holder.Qty.setText(Qty[position]);
        if (!ColorHexa[position].equals("-"))
            holder.ColorHexa.setBackgroundColor(Color.parseColor(ColorHexa[position]));
        else {
            holder.ColorHexa.setVisibility(View.GONE);
            holder.ColorName.setVisibility(View.GONE);
            holder.groupColor.setVisibility(View.GONE);
        }
        final SharedPreferences sharedPreferences = holder.itemView.getContext().getApplicationContext().getSharedPreferences("cart", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        //BigDecimal abc = new BigDecimal(Double.parseDouble(ProdukPrice[position]) * Integer.parseInt(holder.Qty.getText().toString()));
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = 0;
                if (!a.Qty.getText().toString().equals("")) {
                    quant = Integer.parseInt(a.Qty.getText().toString());
                }
                a.Qty.setText(String.valueOf(quant + 1));
            }
        });
        holder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = 0;
                if (!a.Qty.getText().toString().equals("")) {
                    quant = Integer.parseInt(a.Qty.getText().toString());
                }
                if (quant < 1) quant = 1;
                a.Qty.setText(String.valueOf(quant - 1));
            }
        });
        holder.Qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int qtysk = 0;
                if (!a.Qty.getText().toString().equals(""))
                    qtysk = Integer.parseInt(a.Qty.getText().toString());
                BigDecimal abc = new BigDecimal(Double.parseDouble(Qty[pos]) * qtysk);
                String[] tes = sharedPreferences.getString("qty", null).split("-,-");
                //Log.d("perubahan quantity",String.valueOf(Integer.parseInt(ProdukPrice[position].replace(".00","")) *(Integer.parseInt(tes[position]) - qtysk)));
//                holder.subtotal.setText(abc.toPlainString());
                grandTotal.setText("Rp." + String.format("%1$,.2f", Double.parseDouble(grandTotal.getText().toString().substring(3).replace(",", "")) -
                        (Double.parseDouble(ProdukPrice[pos]) * (Integer.parseInt(tes[pos]) - qtysk))));
                inputCurrent(qtysk);
            }

            public void inputCurrent(int qty) {
                String[] tes = sharedPreferences.getString("qty", null).split("-,-");
                String coba = null;
                tes[pos] = String.valueOf(qty);
                for (int i = 0; i < tes.length; i++) {
                    if (coba == null) coba = tes[i];
                    else coba = coba + "-,-" + tes[i];
                }
                editor.putString("qty", coba);
                editor.commit();
            }
        });
        holder.ProductPrice.setText("Rp." + String.format("%1$,.2f", Double.parseDouble(ProdukPrice[position])));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdukInternalID = sharedPreferences.getString(Produk.ARG_INTERNAL_ID, "-,-").split("-,-");
                delete(ProdukInternalID, Produk.ARG_INTERNAL_ID);
                ProdukInternalID = sharedPreferences.getString(Produk.ARG_INTERNAL_ID, "-,-").split("-,-");
                UOMInternalID = sharedPreferences.getString(SpinnerUom.ARG_INTERNAL_ID, "-,-").split("-,-");
                delete(UOMInternalID, SpinnerUom.ARG_INTERNAL_ID);
                UOMInternalID = sharedPreferences.getString(SpinnerUom.ARG_INTERNAL_ID, "-,-").split("-,-");
                ColorInternalID = sharedPreferences.getString(SpinnerColor.ARG_INTERNAL_ID, "-,-").split("-,-");
                delete(ColorInternalID, SpinnerColor.ARG_INTERNAL_ID);
                ColorInternalID = sharedPreferences.getString(SpinnerColor.ARG_INTERNAL_ID, "-,-").split("-,-");
                Qty = sharedPreferences.getString("qty", "-,-").split("-,-");
                delete(Qty, "qty");
                Qty = sharedPreferences.getString("qty", "-,-").split("-,-");
                ProdukName = sharedPreferences.getString(Produk.ARG_PRODUCT_NAME, "-,-").split("-,-");
                delete(ProdukName, Produk.ARG_PRODUCT_NAME);
                ProdukName = sharedPreferences.getString(Produk.ARG_PRODUCT_NAME, "-,-").split("-,-");
                ProdukPrice = sharedPreferences.getString(Produk.ARG_PRODUCT_PRICE, "-,-").split("-,-");
                delete(ProdukPrice, Produk.ARG_PRODUCT_PRICE);
                ProdukPrice = sharedPreferences.getString(Produk.ARG_PRODUCT_PRICE, "-,-").split("-,-");
                ColorName = sharedPreferences.getString(SpinnerColor.ARG_COLOR_NAME, "-,-").split("-,-");
                delete(ColorName, SpinnerColor.ARG_COLOR_NAME);
                ColorName = sharedPreferences.getString(SpinnerColor.ARG_COLOR_NAME, "-,-").split("-,-");
                ColorHexa = sharedPreferences.getString(SpinnerColor.ARG_COLOR_HEXA, "-,-").split("-,-");
                delete(ColorHexa, SpinnerColor.ARG_COLOR_HEXA);
                ColorHexa = sharedPreferences.getString(SpinnerColor.ARG_COLOR_HEXA, "-,-").split("-,-");
                UOMName = sharedPreferences.getString(SpinnerUom.ARG_UOM_ID, "-,-").split("-,-");
                delete(UOMName, SpinnerUom.ARG_UOM_ID);
                UOMName = sharedPreferences.getString(SpinnerUom.ARG_UOM_ID, "-,-").split("-,-");
                ProdukPicture = sharedPreferences.getString(Produk.ARG_PP_1, "-,-").split("-,-");
                delete(ProdukPicture, Produk.ARG_PP_1);
                ProdukPicture = sharedPreferences.getString(Produk.ARG_PP_1, "-,-").split("-,-");
                notifyDataSetChanged();
            }

            public void delete(String[] abc, String header) {
                String coba = null;
                for (int i = 0; i < abc.length; i++) {
                    if (i != pos) {
                        if (coba == null) {coba = abc[i];}
                        else {coba = coba + "-,-" + abc[i];}
                    }
                }
                editor.putString(header, coba);
                editor.commit();
            }
        });
        Double grandTotals = 0.0;
        for (int i = 0; i < ProdukPrice.length; i++) {
            grandTotals += (Double.parseDouble(ProdukPrice[i]) * Integer.parseInt(Qty[i]));
        }
        grandTotal.setText("Rp." + String.format("%1$,.2f", grandTotals));
    }

    @Override
    public int getItemCount() {
        return ProdukInternalID.length;
    }

    public void setImage(ImageView imgvView, String pict) {
        String img = pict.replaceAll(" ", "%20");
        new DownloadImageTask(imgvView)
                .execute("http://home.stationeryone.com/uploaded_img/200x200/" + img);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
