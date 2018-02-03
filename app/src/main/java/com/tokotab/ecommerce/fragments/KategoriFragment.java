package com.tokotab.ecommerce.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.ListCategoryAdapter;
import com.tokotab.ecommerce.lib.DividerItemDecoration;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Produk;

import java.util.ArrayList;
import java.util.List;

public class KategoriFragment extends Fragment {

    private List<ListCategory> listCategories = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel2 = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel3 = new ArrayList<>();
    private ListCategoryAdapter listCategoryAdapter;

    public KategoriFragment() {
    }

    public KategoriFragment(List<ListCategory> listCategories, List<ListCategory> listCategoriesLevel2, List<ListCategory> listCategoriesLevel3) {
        this.listCategories = listCategories;
        this.listCategoriesLevel2 = listCategoriesLevel2;
        this.listCategoriesLevel3 = listCategoriesLevel3;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kategori, container, false);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_kategori_container, new KategoriKontenFragment(listCategories,listCategoriesLevel2,listCategoriesLevel3));
        ft.commit();

        return view;
    }

    public void restart() {

    }


}
