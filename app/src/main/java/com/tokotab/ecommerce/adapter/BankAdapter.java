package com.tokotab.ecommerce.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.activity.DetailActivity;
import com.tokotab.ecommerce.model.Bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwikadarmawan on 6/17/16.
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankHolder> {

    //g dipakai mContext
    private List<Bank> bankList = new ArrayList<>();

    public BankAdapter( List<Bank> bankList) {
        this.bankList = bankList;
    }

    public static class BankHolder extends RecyclerView.ViewHolder  {
        public TextView namaBank, nomorBank, namaorangBank;

        public BankHolder(View view) {
            super(view);
            namaBank = (TextView) view.findViewById(R.id.txt_bank_name);
            nomorBank = (TextView) view.findViewById(R.id.txt_bank_number);
            namaorangBank = (TextView) view.findViewById(R.id.txt_bank_person_name);
        }
    }

    @Override
    public BankAdapter.BankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cardview_bank, parent, false);

        return new BankHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BankHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.namaBank.setText(bank.getBankName());
        holder.nomorBank.setText(bank.getBankNumber());
        holder.namaorangBank.setText(bank.getBankPersonName());
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }
}
