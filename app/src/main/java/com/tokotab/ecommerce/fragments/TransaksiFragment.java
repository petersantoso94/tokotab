package com.tokotab.ecommerce.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.TransaksiAdapter;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.TransaksiDetail;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFragment extends Fragment {
    private List<Transaksi> transaksiList = new ArrayList<>();
    private List<TransaksiDetail> transaksiListDetail = new ArrayList<>();
    private TransaksiAdapter transaksiAdapter;
    FragmentManager fm;


    public TransaksiFragment(FragmentManager fm,List<Transaksi> lt,List<TransaksiDetail> tdl) {
        this.fm = fm;
        transaksiList = lt;
        transaksiListDetail =tdl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        transaksiAdapter = new TransaksiAdapter(getContext(),transaksiList);
        transaksiAdapter.addAll(transaksiList);
        transaksiAdapter.notifyDataSetChanged();

        View v = inflater.inflate(R.layout.fragment_transaksi, container, false);

        final ListView listView = (ListView) v.findViewById(R.id.lv_transaksi);
        listView.setAdapter(transaksiAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("internalID nya",transaksiList.get(position).getInternalID());
                List<TransaksiDetail> td = new ArrayList<TransaksiDetail>();
                for (TransaksiDetail a:transaksiListDetail
                     ) {
                    if(a.getSalesInternalID().equals(transaksiList.get(position).getInternalID())){
                        td.add(a);
                    }
                }
                FragmentManager manager = fm;
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.root_transaksi, new TransaksiDetailFragment(fm,td,transaksiList,transaksiListDetail));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
