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
public class TransporterRecyclerView extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    SearchView searchView;

    public TransporterRecyclerView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transporter_recycler_view, container, false);


        searchView = (SearchView) view.findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(this);

        newsRecyclerview = view.findViewById(R.id.news_tv2);
        mData = new ArrayList<>();

        //fill list news with data

        List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().getTransportes();
        for (TransporterTable transtable : transporterTables){

            String title = transtable.getTransporter_name();
            String c1 = transtable.getTransporter_id_proof_type();
            String c2 = transtable.getTransporter_driving_licence();
            String dt = transtable.getTransporter_id();

            mData.add(new NewsItem(""+title,"ID Proof Type :"+c1+"\n DR No."+c2,
                    ""+dt,R.drawable.professions_driver));


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

            if (name.getTitle().toString().toLowerCase().contains(userInput) || name.getDate().toLowerCase()
            .contains(userInput)){

                newList.add(name);
            }

        }
        newsAdapter.updateList(newList);
        return true;
    }

}
