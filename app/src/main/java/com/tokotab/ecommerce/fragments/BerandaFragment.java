package com.tokotab.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.activity.AllProduct;
import com.tokotab.ecommerce.activity.CartActivity;
import com.tokotab.ecommerce.activity.MainActivity;
import com.tokotab.ecommerce.adapter.ProductAdapter;
import com.tokotab.ecommerce.adapter.SliderImagePagerAdapter;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.SliderImage;

import java.util.ArrayList;
import java.util.List;


public class BerandaFragment extends Fragment {
    private List<Produk> produkList = new ArrayList<>();
    private List<Produk> produkTemp = new ArrayList<>();
    private List<SliderImage> sliderImages = new ArrayList<>();
    private ProductAdapter productAdapter;
    RecyclerView recyclerView;
    private SliderImagePagerAdapter sliderImagePagerAdapter1;
    private MenuItem item = null;
    private Button more;
    private SearchView searchView;

    public BerandaFragment() {}

    public BerandaFragment(List<SliderImage> sliderImages, List<Produk> produk) {
        //inisialisasi data dari inet --cardview beranda produk
        this.sliderImages = sliderImages;
        produkList = new ArrayList<>(produk);
        produkTemp = produk;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        more= (Button) view.findViewById(R.id.btn_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAll = new Intent(getContext(), AllProduct.class);
                startActivity(intentAll);
            }
        });
        ViewPager sliderImageViewPager = (ViewPager) view.findViewById(R.id.slider_beranda1);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_product_beranda);

        sliderImagePagerAdapter1 = new SliderImagePagerAdapter(getContext(), sliderImages);
        sliderImagePagerAdapter1.notifyDataSetChanged();
        productAdapter = new ProductAdapter(this.getContext(), produkList);
        productAdapter.notifyDataSetChanged();

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        sliderImageViewPager.setAdapter(sliderImagePagerAdapter1);

        searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search));

        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.cardview_light_background));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                produkList.clear();
                produkList.addAll(produkTemp);
                productAdapter.notifyDataSetChanged();
                List<Produk> filteredModelList = filter(produkList, query);
                productAdapter.animateTo(filteredModelList);
                recyclerView.scrollToPosition(0);
                return true;
            }
        });

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.search);

    }

    private List<Produk> filter(List<Produk> models, String query) {
        query = query.toLowerCase();
        List<Produk> filteredModelList = new ArrayList<>();
        for (Produk model : models) {
            final String text = model.getProductName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
