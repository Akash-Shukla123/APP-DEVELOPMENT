package com.myapp.tata_parikshan_2019;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripRecyclerView extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    SearchView searchView;


    public TripRecyclerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trip_recycler_view, container, false);

        //make full screen


        //ini view

        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        newsRecyclerview = view.findViewById(R.id.news_tv);
        mData = new ArrayList<>();

        List<TripTable> tripTables = MainActivity.tataParikshanDatabase.myDao().getTrip();
        for (TripTable trp : tripTables){

            String trpId = trp.getTrip_no();
            String materialtype = trp.getMaterial_type();
            String transpID = trp.getTransporterId();
            String vchNo = trp.getVechile_no();
            String destination = trp.getDestination();

            mData.add(new NewsItem(""+materialtype,"",""+trpId,R.drawable.trip));

        }
        //adapter ini

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

            if (name.getTitle().toString().toLowerCase().contains(userInput) || name.getDate().toString().toLowerCase()
            .contains(userInput)){

                newList.add(name);
            }

        }
        newsAdapter.updateList(newList);
        return true;
    }

}
