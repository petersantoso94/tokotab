package com.tokotab.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.activity.DetailActivity;
import com.tokotab.ecommerce.model.Produk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwikadarmawan on 6/17/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    //g dipakai mContext
    private Context fragment;
    private List<Produk> produkList = new ArrayList<>();

    public ProductAdapter(Context fragment, List<Produk> produkList) {
        this.fragment = fragment;
        this.produkList = produkList;
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        public TextView namaProduk, hargaProduk, fakePrice;
        public ImageView gambarProduk;
        public Button diskon_container;

        public ProductHolder(View view) {
            super(view);
            namaProduk = (TextView) view.findViewById(R.id.nama_produk);
            hargaProduk = (TextView) view.findViewById(R.id.harga_produk);
            fakePrice = (TextView) view.findViewById(R.id.harga_fake_produk);
            gambarProduk = (ImageView) view.findViewById(R.id.gambar_produk);
            diskon_container = (Button) view.findViewById(R.id.diskon_container);
        }
    }

    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cardview_beranda, parent, false);

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        final Produk produk = produkList.get(position);
        holder.gambarProduk.setImageDrawable(null);
        produk.setImage(holder.gambarProduk);
//        Picasso.with(fragment)
//                .load(Uri.parse("http://home.stationeryone.com/uploaded_img/200x200/" + produk.getProductPicture1()))
//                .into(holder.gambarProduk);
        String namaBarang = produk.getProductName();
        if (namaBarang.length() > 25)
            namaBarang = namaBarang.substring(0, 25) + "..";
        holder.namaProduk.setText(namaBarang);
        holder.hargaProduk.setText("Rp." + String.format("%1$,.2f", Double.parseDouble(produk.getProductPrice())));
        if (!produk.getFakePrice().trim().equals("0.00")) {
            holder.diskon_container.setVisibility(View.VISIBLE);
            holder.fakePrice.setText("Rp." + String.format("%1$,.2f", Double.parseDouble(produk.getFakePrice())));
            holder.fakePrice.setPaintFlags(holder.fakePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            Double percent = ((Double.parseDouble(produk.getProductPrice()) - Double.parseDouble(produk.getFakePrice())) / Double.parseDouble(produk.getFakePrice())) * 100;
            holder.diskon_container.setText(String.valueOf(percent.intValue()) + "%");
        } else {
            holder.fakePrice.setText("");
            holder.diskon_container.setVisibility(View.INVISIBLE);
        }
        holder.gambarProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(v.getContext(), DetailActivity.class);
                detail.putExtra(Produk.ARG_PRODUCT_NAME, produk.getProductName());
                detail.putExtra(Produk.ARG_INTERNAL_ID, produk.getInternalID());
                detail.putExtra(Produk.ARG_PRODUCT_PRICE, produk.getProductPrice());
                detail.putExtra(Produk.ARG_FAKE_PRICE, produk.getFakePrice());
                detail.putExtra(Produk.ARG_PP_1, produk.getProductPicture1());
                detail.putExtra(Produk.ARG_PP_2, produk.getProductPicture2());
                detail.putExtra(Produk.ARG_PP_3, produk.getProductPicture3());
                detail.putExtra(Produk.ARG_PP_4, produk.getProductPicture4());
                detail.putExtra(Produk.ARG_PRODUCT_CATEGORY, produk.getCategoryInternalID());
                detail.putExtra(Produk.ARG_PRODUCT_COLOR, produk.getProductColor());
                detail.putExtra(Produk.ARG_PRODUCT_DESC, produk.getProductDescription());
                detail.putExtra(Produk.ARG_STATUS, produk.getStatus());
                detail.putExtra(Produk.ARG_PRODUCT_TYPE, produk.getProductType());
                detail.putExtra(Produk.ARG_PRODUCT_ID, produk.getProductID());
                detail.putExtra(Produk.ARG_PRODUCT_NEW, produk.getNewProduct());
                detail.putExtra(Produk.ARG_PRODUCT_TOP, produk.getTopProduct());
                v.getContext().startActivity(detail);
            }
        });
        holder.hargaProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(v.getContext(), DetailActivity.class);
                detail.putExtra(Produk.ARG_PRODUCT_NAME, produk.getProductName());
                detail.putExtra(Produk.ARG_INTERNAL_ID, produk.getInternalID());
                detail.putExtra(Produk.ARG_PRODUCT_PRICE, produk.getProductPrice());
                detail.putExtra(Produk.ARG_FAKE_PRICE, produk.getFakePrice());
                detail.putExtra(Produk.ARG_PP_1, produk.getProductPicture1());
                detail.putExtra(Produk.ARG_PP_2, produk.getProductPicture2());
                detail.putExtra(Produk.ARG_PP_3, produk.getProductPicture3());
                detail.putExtra(Produk.ARG_PP_4, produk.getProductPicture4());
                detail.putExtra(Produk.ARG_PRODUCT_CATEGORY, produk.getCategoryInternalID());
                detail.putExtra(Produk.ARG_PRODUCT_COLOR, produk.getProductColor());
                detail.putExtra(Produk.ARG_PRODUCT_DESC, produk.getProductDescription());
                detail.putExtra(Produk.ARG_STATUS, produk.getStatus());
                detail.putExtra(Produk.ARG_PRODUCT_TYPE, produk.getProductType());
                detail.putExtra(Produk.ARG_PRODUCT_ID, produk.getProductID());
                detail.putExtra(Produk.ARG_PRODUCT_NEW, produk.getNewProduct());
                detail.putExtra(Produk.ARG_PRODUCT_TOP, produk.getTopProduct());
                v.getContext().startActivity(detail);
            }
        });
        holder.namaProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(v.getContext(), DetailActivity.class);
                detail.putExtra(Produk.ARG_PRODUCT_NAME, produk.getProductName());
                detail.putExtra(Produk.ARG_INTERNAL_ID, produk.getInternalID());
                detail.putExtra(Produk.ARG_PRODUCT_PRICE, produk.getProductPrice());
                detail.putExtra(Produk.ARG_FAKE_PRICE, produk.getFakePrice());
                detail.putExtra(Produk.ARG_PP_1, produk.getProductPicture1());
                detail.putExtra(Produk.ARG_PP_2, produk.getProductPicture2());
                detail.putExtra(Produk.ARG_PP_3, produk.getProductPicture3());
                detail.putExtra(Produk.ARG_PP_4, produk.getProductPicture4());
                detail.putExtra(Produk.ARG_PRODUCT_CATEGORY, produk.getCategoryInternalID());
                detail.putExtra(Produk.ARG_PRODUCT_COLOR, produk.getProductColor());
                detail.putExtra(Produk.ARG_PRODUCT_DESC, produk.getProductDescription());
                detail.putExtra(Produk.ARG_STATUS, produk.getStatus());
                detail.putExtra(Produk.ARG_PRODUCT_TYPE, produk.getProductType());
                detail.putExtra(Produk.ARG_PRODUCT_ID, produk.getProductID());
                detail.putExtra(Produk.ARG_PRODUCT_NEW, produk.getNewProduct());
                detail.putExtra(Produk.ARG_PRODUCT_TOP, produk.getTopProduct());
                v.getContext().startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public Produk removeItem(int position) {
        final Produk a = produkList.remove(position);
        notifyItemRemoved(position);
        return a;
    }

    public void addItem(int position, Produk a) {
        produkList.add(position, a);
        notifyItemInserted(position);
    }

    public void moveItem(int from, int to) {
        final Produk a = produkList.remove(from);
        produkList.add(to, a);
        notifyItemMoved(from, to);
    }

    public void animateTo(List<Produk> a) {
        applyAndAnimateRemovals(a);
        applyAndAnimateAdditions(a);
        applyAndAnimateMovedItems(a);
    }

    private void applyAndAnimateRemovals(List<Produk> newModels) {
        for (int i = produkList.size() - 1; i >= 0; i--) {
            final Produk model = produkList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Produk> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Produk model = newModels.get(i);
            if (!produkList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Produk> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Produk model = newModels.get(toPosition);
            final int fromPosition = produkList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
