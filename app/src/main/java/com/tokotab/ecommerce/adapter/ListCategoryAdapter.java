package com.tokotab.ecommerce.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.fragments.KategoriFragment;
import com.tokotab.ecommerce.fragments.KategoriFragmentSectioned;
import com.tokotab.ecommerce.fragments.KategoriKontenFragment;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Produk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private List<ListCategory> mListCategories = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel2 = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel3 = new ArrayList<>();
    FragmentManager fragmentManager;
    Fragment fragment;
    SearchView searchView;

    public void add(ListCategory s, int position) {
        position = position == -1 ? getItemCount() : position;
        mListCategories.add(position, s);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            mListCategories.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public final TextView TVCategoryName;
        public final TextView TVCountProduk;
        public final ImageView icArrowRight;

        public CategoryViewHolder(View view) {
            super(view);
            TVCategoryName = (TextView) view.findViewById(R.id.TV_category_name);
            TVCountProduk = (TextView) view.findViewById(R.id.TV_count_produk);
            icArrowRight = (ImageView) view.findViewById(R.id.ic_arrow_right);
        }
    }

    public ListCategoryAdapter(Context context, List<ListCategory> data, List<ListCategory> listCategoriesLevel2, List<ListCategory> listCategoriesLevel3,FragmentManager fm, Fragment fragment,  SearchView searchView) {
        mContext = context;
        this.fragment = fragment;
        this.fragmentManager = fm;
        this.searchView=searchView;
        this.listCategoriesLevel2 = listCategoriesLevel2;
        this.listCategoriesLevel3 = listCategoriesLevel3;
        if (data != null)
            mListCategories = new ArrayList<>(data);
        else mListCategories = new ArrayList<>();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_view_category, parent, false);


        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {
        holder.TVCategoryName.setText(mListCategories.get(position).getCategoryName().toString());
        holder.TVCountProduk.setText(mListCategories.get(position).getJumlah().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.fragment_kategori_container, new KategoriFragmentSectioned(mListCategories, mListCategories.get(position).getCategoryName(),recyclerView));
                ft.remove(fragment).add(R.id.fragment_kategori_container,new KategoriFragmentSectioned(mListCategories,listCategoriesLevel2,listCategoriesLevel3, mListCategories.get(position)));
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        if (mListCategories.get(position).getJumlah().toString().equals("0")) {
            holder.icArrowRight.setVisibility(View.GONE);
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
