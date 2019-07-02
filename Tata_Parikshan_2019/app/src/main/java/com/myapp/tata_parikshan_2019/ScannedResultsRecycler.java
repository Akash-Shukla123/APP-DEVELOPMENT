package com.myapp.tata_parikshan_2019;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScannedResultsRecycler extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    SearchView searchView;
    TextView totalTxt,CountTxt;

    public ScannedResultsRecycler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scanned_results_recycler, container, false);

        searchView = (SearchView) view.findViewById(R.id.searchView4);
        totalTxt = view.findViewById(R.id.total_results);
        CountTxt = view.findViewById(R.id.scanned_results);

        searchView.setOnQueryTextListener(this);

        newsRecyclerview = view.findViewById(R.id.news_tv4);
        mData = new ArrayList<>();

        ScannerActivity scannerActivity = new ScannerActivity();

        int a=0,b=0;
        List<TotalResults> totalResults = MainActivity.tataParikshanDatabase.myDao().getTotal();
        for (TotalResults totalResults1 : totalResults){
            a=a+totalResults1.getTotal_scan();
        }

        totalTxt.setText("Total Scan : "+a);

        List<ScannerTable> scannerTables = MainActivity.tataParikshanDatabase.myDao().getScanner();
        for (ScannerTable scannerTable: scannerTables){
            b= scannerTable.getResults_Count();
            if (b==0){
                mData.clear();
            }
            else {
                mData.add(new NewsItem("" + scannerTable.getDate() + "\n" + scannerTable.getTime(),
                        "" + scannerTable.getResults(), "" + scannerTable.getSid(), R.mipmap.ic_desc));
            }
        }

        CountTxt.setText("Results Found : " +b);

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
