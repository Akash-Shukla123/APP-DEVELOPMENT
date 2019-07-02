package com.myapp.tata_parikshan_2019;


import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_trip_report extends Fragment implements DateTimePicker.OnFragmentInteractionListener
,SwipeRefreshLayout.OnRefreshListener{

    RecyclerView newsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    SearchView searchView;

    private AutoCompleteTextView rep_transporter;
    private Spinner rep_vechile_type;
    private Button dateTime, submit;
    private String infodateTime,checker="false";
    private int REQUEST_CODE = 11;
    public SwipeRefreshLayout swipeRefreshLayout;

    public fragment_trip_report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_trip_report, container, false);

        newsRecyclerview = view.findViewById(R.id.trip_report_recycler);
        mData = new ArrayList<>();

        rep_transporter = view.findViewById(R.id.Rep_transporter);
        rep_vechile_type = view.findViewById(R.id.rep_vechile_type);
        dateTime = view.findViewById(R.id.rep_dateTime);
        submit = view.findViewById(R.id.rep_submit);

        List<TransporterTable> tableList = MainActivity.tataParikshanDatabase.myDao().getTransportes();
        final List<String> Transnames = new ArrayList<String>();
        for (TransporterTable transporters: tableList){
            String transporter_info = transporters.getTransporter_name();
            Transnames.add(transporter_info);
        }
        String TransnamesArray[] = new String[Transnames.size()];
        String[] TRANSPORTERS = Transnames.toArray(TransnamesArray);

        ArrayAdapter<String> TransarrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,TRANSPORTERS);
        rep_transporter.setAdapter(TransarrayAdapter);

        swipeRefreshLayout = view.findViewById(R.id.rep_swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new DateTimePicker();
                newFragment.show(getFragmentManager(), "DatePicker");
                newFragment.setTargetFragment(fragment_trip_report.this, REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.clear();

                List<TransporterTable> transporterTables = MainActivity.tataParikshanDatabase.myDao().findByTransporterName(rep_transporter.getText().toString());
                if (transporterTables != null) {
                List<VechileTable> vechileTables = MainActivity.tataParikshanDatabase.myDao().findByVechileType(rep_vechile_type.getSelectedItem().toString());
                if (vechileTables != null){

                for (TransporterTable trnps : transporterTables) {
                    for (VechileTable vech : vechileTables) {
                        List<TripTable> tripTableList = MainActivity.tataParikshanDatabase.myDao().findByTripDetails(trnps.getTransporter_id(), vech.getVechile_id());
                        if (tripTableList != null) {

                            for (TripTable table : tripTableList) {

                                String date = table.getStartDateTime();

                                String info1 = "", info2 = "";
                                char arr[] = new char[30];
                                date.getChars(0, 13, arr, 0);

                                for (int i = 0; i <= 7; i++) {
                                    info1 = info1 + arr[i];
                                }

                                for (int i = 8; i <= 13; i++) {
                                    if (i == 10) {
                                        info2 = info2 + ":" + arr[i];
                                    }
                                    if (i == 11) {
                                        info2 = info2 + ":" + arr[i];
                                    } else {
                                        info2 = info2 + arr[i];
                                    }
                                }

                                if (info1.equals(infodateTime)) {
                                    int a = Integer.parseInt(table.getGrossWeight()) - Integer.parseInt(table.getTareWeight());

                                    mData.add(new NewsItem("Name : " + trnps.getTransporter_name().toString(),
                                            "Vechile Type : " + vech.getVechile_type().toString() +
                                                    "\n Material Type : " + table.getMaterial_type() +
                                                    "\n Material Description : " + table.getMaterial_description() +
                                                    "\n Net Weight : " + a +
                                                    "\n Loading Area : " + table.getLoading_area() +
                                                    "\n Destination : " + table.getDestination() +
                                                    "\n Status : " + table.getStatus()
                                            , "Time : " + info2, R.drawable.circle_back));

                                    onRefresh();
                                    checker = "true";
                                }
                                else {
                                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } else {
                            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
                else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
        }
                else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
                if (checker.equals("false")){
                    Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                }
            }
        });

        newsAdapter = new NewsAdapter(getContext(),mData);
        newsRecyclerview.setAdapter(newsAdapter);
        newsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onFragmentInteraction(String info, String infodb) {
        dateTime.setText(info.toString());
        infodateTime = infodb.toString();
    }

    public void onRefreshItem(){
        List<NewsItem> newList = new ArrayList<>();

        for (NewsItem name: mData){
            newList.add(name);
        }
        newsAdapter.updateList(newList);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        onRefreshItem();
    }

}
