package com.tokotab.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.activity.AllProduct;
import com.tokotab.ecommerce.activity.CategoryProduct;
import com.tokotab.ecommerce.model.ListCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dwikadarmawan on 6/21/16.
 */
public class ListCategorySectionedAdapter extends RecyclerView.Adapter<ListCategorySectionedAdapter.SectionedCategoryHolder> {

    private Context mContext;
    private List<ListCategory> mListCategories = new ArrayList<>();
    FragmentManager fragmentManager;
    Fragment fragment;
    private ListCategory selectedCategory;

    public ListCategorySectionedAdapter(Context context, List<ListCategory> data, FragmentManager fm, Fragment fragment) {
        mContext = context;
        this.fragment = fragment;
        this.fragmentManager = fm;
        mListCategories = new ArrayList<>(data);
    }

    @Override
    public SectionedCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_view_category, parent, false);

        return new SectionedCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionedCategoryHolder holder, int position) {
        holder.TVCategoryName.setText(mListCategories.get(position).getCategoryName());
        holder.TVCountProduk.setText(mListCategories.get(position).getJumlah());
        holder.icArrowRight.setVisibility(View.GONE);
        final String internal = mListCategories.get(position).getInternalID();
        final String nama = mListCategories.get(position).getCategoryName();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent produk = new Intent(fragment.getContext(), CategoryProduct.class);
                produk.putExtra("internalID",internal);
                produk.putExtra("nama",nama);
                fragment.getActivity().startActivity(produk);
            }
        });
    }

    public static class SectionedCategoryHolder extends RecyclerView.ViewHolder {
        public final TextView TVCategoryName;
        public final TextView TVCountProduk;
        public final ImageView icArrowRight;

        public SectionedCategoryHolder(View view) {
            super(view);
            TVCategoryName = (TextView) view.findViewById(R.id.TV_category_name);
            TVCountProduk = (TextView) view.findViewById(R.id.TV_count_produk);
            icArrowRight = (ImageView) view.findViewById(R.id.ic_arrow_right);
        }
    }

    @Override
    public int getItemCount() {
        return mListCategories.size();
    }
    public ListCategory removeItem(int position){
        final ListCategory a = mListCategories.remove(position);
        notifyItemRemoved(position);
        return  a;
    }

    public void addItem(int position, ListCategory a){
        mListCategories.add(position,a);
        notifyItemInserted(position);
    }

    public void moveItem(int from, int to){
        final ListCategory a =mListCategories.remove(from);
        mListCategories.add(to,a);
        notifyItemMoved(from,to);
    }

    public void animateTo(List<ListCategory> a){
        applyAndAnimateRemovals(a);
        applyAndAnimateAdditions(a);
        applyAndAnimateMovedItems(a);
    }
    private void applyAndAnimateRemovals(List<ListCategory> newModels) {
        for (int i = mListCategories.size() - 1; i >= 0; i--) {
            final ListCategory model = mListCategories.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<ListCategory> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final ListCategory model = newModels.get(i);
            if (!mListCategories.contains(model)) {
                addItem(i, model);
            }
        }
    }
    private void applyAndAnimateMovedItems(List<ListCategory> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final ListCategory model = newModels.get(toPosition);
            final int fromPosition = mListCategories.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

}
