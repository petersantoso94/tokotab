package com.tokotab.ecommerce.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.model.User;

public class ProfilFragment extends Fragment {

    private TextView txt_telp,txt_alamat,txt_email;
    private SharedPreferences sharedPreferences;
    public ProfilFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPreferences = getContext().getSharedPreferences("user",0);
        View v = inflater.inflate(R.layout.fragment_profil, container, false);
        txt_telp = (TextView) v.findViewById(R.id.tvPhone_profil);
        txt_alamat = (TextView) v.findViewById(R.id.tvPlace_profil);
        txt_email = (TextView) v.findViewById(R.id.tvEmail_profil);

        txt_telp.setText(sharedPreferences.getString(User.MEMBER_PHONE,"-"));
        txt_alamat.setText(sharedPreferences.getString(User.MEMBER_ADDRESS,"-"));
        txt_email.setText(sharedPreferences.getString(User.MEMBER_EMAIL,"-"));
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
