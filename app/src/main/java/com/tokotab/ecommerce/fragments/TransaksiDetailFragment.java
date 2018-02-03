package com.tokotab.ecommerce.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.TransaksiDetailAdapter;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.TransaksiDetail;

import java.util.ArrayList;
import java.util.List;

public class TransaksiDetailFragment extends Fragment {

    TransaksiDetailAdapter transaksiDetailAdapter;
    List<TransaksiDetail> transaksiDetail = new ArrayList<>();
    private List<Transaksi> transaksiList = new ArrayList<>();
    private List<TransaksiDetail> transaksiListDetail = new ArrayList<>();
    FragmentManager fm;
    LinearLayout header;

    public TransaksiDetailFragment(FragmentManager fm,List<TransaksiDetail> transaksiDetail,List<Transaksi> lt,List<TransaksiDetail> tdl) {
        this.fm = fm;
        this.transaksiDetail=transaksiDetail;
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
        transaksiDetailAdapter = new TransaksiDetailAdapter(getContext(),transaksiDetail);
        transaksiDetailAdapter.addAll(transaksiDetail);
        transaksiDetailAdapter.notifyDataSetChanged();

        View v = inflater.inflate(R.layout.fragment_transaksi_detail, container, false);
        header = (LinearLayout) v.findViewById(R.id.header_trans_layout);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(TransaksiDetailFragment.this).add(R.id.root_transaksi, new TransaksiFragment(fm,transaksiList,transaksiListDetail));
                ft.disallowAddToBackStack();
                ft.commit();
//                categoryRecyclerView.setVisibility(View.VISIBLE);
//                header.setVisibility(View.GONE);
//                recyclerViewSectioned.setVisibility(View.GONE);
            }
        });
        ListView listView = (ListView) v.findViewById(R.id.lv_transaksi_detail);
        listView.setAdapter(transaksiDetailAdapter);

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
