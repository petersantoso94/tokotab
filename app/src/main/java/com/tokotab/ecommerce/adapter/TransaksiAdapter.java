package com.tokotab.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Transaksi;

import java.util.List;


public class TransaksiAdapter extends ArrayAdapter<Transaksi> {

    Context context;
    List<Transaksi> transaksiList;
    public TransaksiAdapter(Context context, List<Transaksi> lt) {
        super(context, R.layout.list_transaksi);
        this.context = context;
        transaksiList = lt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_transaksi, parent, false);
        Transaksi transaksi = transaksiList.get(position);

        TextView tv_transaksi_po, tv_transaksi_date, tv_transaksi_status, tv_transaksi_total;

        tv_transaksi_po = (TextView) v.findViewById(R.id.tv_transaksi_po);
        tv_transaksi_date = (TextView) v.findViewById(R.id.tv_transaksi_date);
        tv_transaksi_status = (TextView) v.findViewById(R.id.tv_transaksi_status);
        tv_transaksi_total = (TextView) v.findViewById(R.id.tv_transaksi_total);

        String status = "";
        if(transaksi.getStatus().equals("0")){
            status = "menunggu";
        }else if(transaksi.getStatus().equals("1")){
            status = "telah diproses";
        }else if(transaksi.getStatus().equals("0")){
            status = "batal";
        }
        String [] tanggal = transaksi.getTanggal().split(" ");
        tv_transaksi_po.setText(transaksi.getNoPo());
        tv_transaksi_date.setText(tanggal[0]);
        tv_transaksi_status.setText(status);
        tv_transaksi_total.setText("Rp."+String.format("%,d",Integer.parseInt(transaksi.getGrandTotal().replace(".00","")))+",-");

        return v;
    }
}
