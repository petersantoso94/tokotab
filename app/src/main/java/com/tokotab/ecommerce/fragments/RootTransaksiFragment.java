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

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.TransaksiDetail;

import java.util.List;

 public class RootTransaksiFragment extends Fragment {
    FragmentManager fm;
    List<Transaksi> transaksiList;
     List<TransaksiDetail> transaksiListDetail;
    public RootTransaksiFragment(FragmentManager fm, List<Transaksi> lt,List<TransaksiDetail> tdl) {
        // Required empty public constructor
        this.fm = fm;
        transaksiList = lt;
        transaksiListDetail = tdl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_root_transaksi, container, false);

        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.root_transaksi, new TransaksiFragment(fm,transaksiList,transaksiListDetail));
        transaction.commit();

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
