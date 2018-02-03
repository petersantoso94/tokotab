package com.tokotab.ecommerce.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.ListCategoryAdapter;
import com.tokotab.ecommerce.adapter.ListCategorySectionedAdapter;
import com.tokotab.ecommerce.lib.DividerItemDecoration;
import com.tokotab.ecommerce.lib.SimpleSectionedRecyclerViewAdapter;
import com.tokotab.ecommerce.model.ListCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class KategoriFragmentSectioned extends Fragment {

    private List<ListCategory> mListCategories = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel2 = new ArrayList<>();
    private List<ListCategory> listCategoriesLevel3 = new ArrayList<>();
    List<ListCategory> selectedLVL3 = new ArrayList<>();
    List<ListCategory> selectedLVL3Temp = new ArrayList<>();
    private ListCategorySectionedAdapter listCategorySectionedAdapter;
    private ListCategory selectedCategory;
    private Integer jumlah = 0;
    private List<Integer> jumlahLvl2 = new ArrayList<>();
    LinearLayout header,forRecycler;
    RecyclerView recyclerViewSectioned;
    private SearchView searchView;

    public KategoriFragmentSectioned() {

    }

    public KategoriFragmentSectioned(List<ListCategory> mListCategories, List<ListCategory> listCategoriesLevel2, List<ListCategory> listCategoriesLevel3, ListCategory selectedCategory) {
        this.mListCategories = mListCategories;
        this.listCategoriesLevel2 = listCategoriesLevel2;
        this.listCategoriesLevel3 = listCategoriesLevel3;
        this.selectedCategory = selectedCategory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("data", selectedCategory.getCategoryName());
        View view = inflater.inflate(R.layout.fragment_kategori_sectioned, container, false);

        header = (LinearLayout) view.findViewById(R.id.header_category_linear_layout);
        forRecycler= (LinearLayout) view.findViewById(R.id.linear_for_recycler);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(KategoriFragmentSectioned.this).add(R.id.fragment_kategori_container, new KategoriFragment(mListCategories, listCategoriesLevel2, listCategoriesLevel3));
                ft.disallowAddToBackStack();
                ft.commit();
//                categoryRecyclerView.setVisibility(View.VISIBLE);
//                header.setVisibility(View.GONE);
//                recyclerViewSectioned.setVisibility(View.GONE);
            }
        });

        TextView TVHeaderCategory = (TextView) view.findViewById(R.id.TV_sectioned_category_name);
        TVHeaderCategory.setText(selectedCategory.getCategoryName());
        recyclerViewSectioned = (RecyclerView) view.findViewById(R.id.list_category_sectioned_recyclerview);
        recyclerViewSectioned.setHasFixedSize(true);
        recyclerViewSectioned.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerViewSectioned.setLayoutManager(linearLayout);
        recyclerViewSectioned.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        FragmentManager fm = getFragmentManager();
        Boolean pertama = true;
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
        Integer count = 0;
        //Sections
        for (ListCategory lvl2 : listCategoriesLevel2
                ) {
            if (lvl2.getParentInternalID().equals(selectedCategory.getInternalID())) {
                if (pertama) {
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, lvl2.getCategoryName()));
                    pertama = false;
                } else {
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(count, lvl2.getCategoryName()));
                }
                jumlah++;
                for (ListCategory lvl3 : listCategoriesLevel3
                        ) {
                    if (lvl3.getParentInternalID().equals(lvl2.getInternalID())) {
                        selectedLVL3.add(lvl3);
                        selectedLVL3Temp.add(lvl3);
                        jumlahLvl2.add(jumlah);
                        count++;
                    }
                }
            }
        }
        searchView = (SearchView) view.findViewById(R.id.search_kategori_sectioned);
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
                Log.d("position",String.valueOf(filter(selectedLVL3,query)));
                recyclerViewSectioned.scrollToPosition((filter(selectedLVL3,query)));//(nestedScrollView.getBottom()/selectedLVL3.size())
                linearLayout.scrollToPosition(filter(selectedLVL3,query));
                recyclerViewSectioned.setLayoutManager(linearLayout);
                return true;
            }
        });

        listCategorySectionedAdapter = new ListCategorySectionedAdapter(getContext(), selectedLVL3Temp, fm, KategoriFragmentSectioned.this);
        listCategorySectionedAdapter.notifyDataSetChanged();
        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getContext(), R.layout.section_category_header, R.id.section_header_text, listCategorySectionedAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        recyclerViewSectioned.setAdapter(mSectionedAdapter);

        return view;
    }

    private Integer filter(List<ListCategory> models, String query) {
        query = query.toLowerCase();
        Integer postition = 0;
        Boolean first = true;
//        String internalID = "";
//        for (ListCategory lvl2 : listCategoriesLevel2
//                ) {
//            if(lvl2.getCategoryName().contains(query)){
//                internalID = lvl2.getInternalID();
//            }
//        }
        Integer count=0;
        for (ListCategory model : models) {
            final String text = model.getCategoryName().toLowerCase();
            if (text.contains(query) && first) {
                postition = count+jumlahLvl2.get(count);
                first=false;
            }
//            else{
//                if(model.getParentInternalID().equals(internalID)){
//                    filteredModelList.add(model);
//                }
//            }
            count++;
        }
        return postition;
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
