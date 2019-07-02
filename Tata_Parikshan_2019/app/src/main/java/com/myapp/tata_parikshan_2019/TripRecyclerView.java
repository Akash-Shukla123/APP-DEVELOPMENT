package com.myapp.tata_parikshan_2019;


import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
            String vchNo=trp.getVechile_Id();
            List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechID(trp.getVechile_Id());
            for (VechileTable vechileTable: vechileTables){
                vchNo = vechileTable.getVechile_no();
            }
            String destination = trp.getDestination();

            String info="";
            List<TransporterTable> list = MainActivity.tataParikshanDatabase.myDao().findByIds(transpID);
            for (TransporterTable table : list){
                info = table.getTransporter_name();
            }

            String info1 = "";
            char[] arr1 = new char[30];
            trp.getEndDateTime().getChars(0,14,arr1,0);

            for (int j = 0; j <= 14; j++) {
                if (j == 4) {
                    info1 = info1+"/"+arr1[j];
                }
                else if (j == 6) {
                    info1 = info1+"/"+arr1[j];
                }
                else if (j == 8) {
                    info1 = info1 + " Time " + arr1[j];
                }
                else if (j == 10) {
                    info1 = info1 + ":" + arr1[j];
                }
                else if (j == 12) {
                    info1 = info1 + ":" + arr1[j];
                }
                else {
                    info1 = info1 + arr1[j];
                }
            }

            String info2 = "";
            char arr[] = new char[30];
            trp.getStartDateTime().getChars(0, 14, arr, 0);

            for (int i = 0; i <= 14; i++) {
                if (i == 4) {
                    info2 = info2+"/"+arr[i];
                }
                else if (i == 6) {
                    info2 = info2+"/"+arr[i];
                }
                else if (i == 8) {
                    info2 = info2 + " Time " + arr[i];
                }
                else if (i == 10) {
                    info2 = info2 + ":" + arr[i];
                }
                else if (i == 12) {
                    info2 = info2 + ":" + arr[i];
                }
                else {
                    info2 = info2 + arr[i];
                }
            }

            mData.add(new NewsItem(""+materialtype+"\n\n->\t"+trp.getStatus(),"Name : "+info+"\t\t\t Vechile No. : "+vchNo+"\n Start Date-Time : "+info2+
                    "\n End Date-Time : "+info1+"\n From : "+destination+ "\t\t To : "+trp.getLoading_area()+
                    "\n Material Description : "+trp.getMaterial_description()+"\n Tare Weight : "+trp.getTareWeight()+"\t\t Gross Weight : "+trp.getGrossWeight(),""+trpId,R.drawable.trip));

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
