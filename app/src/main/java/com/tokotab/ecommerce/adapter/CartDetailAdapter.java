package com.tokotab.ecommerce.adapter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.MyViewHolder> {
    public String[] ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName, ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture;
    public TextView grandTotal;

    public CartDetailAdapter(TextView grandTotal, String[] produkInternalID, String[] UOMInternalID,
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
        public LinearLayout groupColor;
        public TextView Qty;
        public ImageView ProductPict;
        public View ColorHexa;

        public MyViewHolder(View itemView) {
            super(itemView);
            ProductName = (TextView) itemView.findViewById(R.id.Cart_detail_Product_Name);
            ProductPrice = (TextView) itemView.findViewById(R.id.cart_detail_price);
            ColorName = (TextView) itemView.findViewById(R.id.cart_detail_color_text);
            UOM = (TextView) itemView.findViewById(R.id.cart_detail_uom);
            Qty = (TextView) itemView.findViewById(R.id.cart_detail_qty);
            groupColor = (LinearLayout) itemView.findViewById(R.id.cart_detail_color_group);
            ProductPict = (ImageView) itemView.findViewById(R.id.cart_detail_image);
            ColorHexa = (View) itemView.findViewById(R.id.cart_detail_color);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_konten_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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
        holder.ProductPrice.setText("Rp." + String.format("%,d", Integer.parseInt(ProdukPrice[position].replace(".00", ""))));

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
