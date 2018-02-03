package com.tokotab.ecommerce.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.ListCategoryAdapter;
import com.tokotab.ecommerce.lib.DividerItemDecoration;
import com.tokotab.ecommerce.model.ListCategory;
import com.tokotab.ecommerce.model.Produk;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KategoriKontenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KategoriKontenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KategoriKontenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<ListCategory> listCategories = new ArrayList<>();
    private List<ListCategory> listCategoriesTemp = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel2 = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel3 = new ArrayList<>();
    private ListCategoryAdapter listCategoryAdapter;
    private MenuItem item = null;
    private SearchView searchView;
    RecyclerView recyclerViewListCategory;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public KategoriKontenFragment() {
        // Required empty public constructor
    }

    public KategoriKontenFragment(List<ListCategory> listCategories, List<ListCategory> listCategoriesLevel2, List<ListCategory> listCategoriesLevel3) {
        this.listCategories = new ArrayList<>(listCategories);
        listCategoriesTemp = listCategories;
        this.listCategoriesLevel2 = listCategoriesLevel2;
        this.listCategoriesLevel3 = listCategoriesLevel3;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KategoriKontenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KategoriKontenFragment newInstance(String param1, String param2) {
        KategoriKontenFragment fragment = new KategoriKontenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kategori_konten, container, false);
        recyclerViewListCategory = (RecyclerView) rootView.findViewById(R.id.list_category_recyclerview);
        recyclerViewListCategory.setHasFixedSize(true);
        recyclerViewListCategory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewListCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = (SearchView) rootView.findViewById(R.id.search_kategori);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Cari Kategori");

        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.cardview_light_background));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                listCategories.clear();
                listCategories.addAll(listCategoriesTemp);
                listCategoryAdapter.notifyDataSetChanged();
                List<ListCategory> filteredModelList = filter(listCategories, query);
                listCategoryAdapter.animateTo(filteredModelList);
                recyclerViewListCategory.scrollToPosition(0);
                return true;
            }
        });

        FragmentManager fm = getFragmentManager();
        recyclerViewListCategory.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        listCategoryAdapter = new ListCategoryAdapter(getContext(), listCategoriesTemp,listCategoriesLevel2,listCategoriesLevel3, fm, KategoriKontenFragment.this,searchView);
        listCategoryAdapter.notifyDataSetChanged();
        recyclerViewListCategory.setAdapter(listCategoryAdapter);
        return rootView;
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//        super.onCreateOptionsMenu(menu, inflater);
//        item = menu.findItem(R.id.search);
//        searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                listCategories.clear();
//                listCategories.addAll(listCategoriesTemp);
//                listCategoryAdapter.notifyDataSetChanged();
//                List<ListCategory> filteredModelList = filter(listCategories, query);
//                listCategoryAdapter.animateTo(filteredModelList);
//                recyclerViewListCategory.scrollToPosition(0);
//                return true;
//            }
//        });
//    }
    private List<ListCategory> filter(List<ListCategory> models, String query) {
        query = query.toLowerCase();
        List<ListCategory> filteredModelList = new ArrayList<>();
        for (ListCategory model : models) {
            final String text = model.getCategoryName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
