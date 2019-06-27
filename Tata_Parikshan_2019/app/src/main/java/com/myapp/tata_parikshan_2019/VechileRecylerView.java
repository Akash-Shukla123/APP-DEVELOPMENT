package com.myapp.tata_parikshan_2019;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VechileRecylerView extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    SearchView searchView;


    public VechileRecylerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vechile_recyler_view, container, false);

        searchView = (SearchView) view.findViewById(R.id.searchView3);
        searchView.setOnQueryTextListener(this);

        newsRecyclerview = view.findViewById(R.id.news_tv3);
        mData = new ArrayList<>();

        //fill list news with data

        List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().getVechile();
        for (VechileTable vch : vechileTables){
            String vechNo = vch.getVechile_no();
            String vchId = vch.getVechile_id();
            String vchType = vch.getVechile_type();

            mData.add(new NewsItem("NO.:"+vechNo,"Vechile Type :"+vchType,
                    ""+vchId,R.drawable.vechile));
        }

        newsAdapter = new NewsAdapter(getContext(),mData);
        newsRecyclerview.setAdapter(newsAdapter);
        newsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String userInput = s.toLowerCase();
        List<NewsItem> newList = new ArrayList<>();

        for (NewsItem name: mData){

            if (name.getTitle().toString().toLowerCase().contains(userInput) || name.getDate().toString()
                    .toLowerCase().contains(userInput)){

                newList.add(name);
            }

        }
        newsAdapter.updateList(newList);
        return true;
    }

}
