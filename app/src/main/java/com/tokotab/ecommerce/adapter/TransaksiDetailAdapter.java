package com.tokotab.ecommerce.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.TransaksiDetail;

import java.util.List;


public class TransaksiDetailAdapter extends ArrayAdapter<TransaksiDetail> {

    Context context;
    List<TransaksiDetail> detailList;
    public TransaksiDetailAdapter(Context context, List<TransaksiDetail> td) {
        super(context, R.layout.list_transaksi_detail);
        this.context = context;
        detailList = td;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.list_transaksi_detail, parent, false);

        TransaksiDetail transaksiDetail = detailList.get(position);

        TextView tv_transaksi_detail_nama = (TextView) v.findViewById(R.id.tv_transaksi_detail_nama);
        TextView tv_transaksi_total = (TextView) v.findViewById(R.id.tv_transaksi_total);
        TextView tv_transaksi_warna = (TextView) v.findViewById(R.id.tv_transaksi_warna);
        TextView tv_transaksi_subtotal = (TextView) v.findViewById(R.id.tv_transaksi_subtotal);
        View v_color_transaksi_detail = v.findViewById(R.id.v_color_transaksi_detail);

        String totalConcatenate = transaksiDetail.getQty() + " " + transaksiDetail.getUOM() + " @ " + transaksiDetail.getPrice();

        tv_transaksi_detail_nama.setText(transaksiDetail.getProductName());
        tv_transaksi_total.setText(totalConcatenate);
        if(!transaksiDetail.getColorName().equals("-")) {
            tv_transaksi_warna.setText(transaksiDetail.getColorName());
            v_color_transaksi_detail.setBackgroundColor(Color.parseColor(transaksiDetail.getColorHexa()));
        }else {
            tv_transaksi_warna.setVisibility(View.GONE);
            v_color_transaksi_detail.setVisibility(View.GONE);
        }
        tv_transaksi_subtotal.setText(transaksiDetail.getSubtotal());

        return v;
    }
}
