package com.myapp.tata_parikshan_2019;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateTimePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String info, String infodb);
    }

    int day,month,year,hour,minute;
    String day_x,month_x,year_x;
    String infoData1,infoData2;
    private OnFragmentInteractionListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year_x = Integer.toString(i);
        month_x = Integer.toString(i1+1);
        day_x = Integer.toString(i2);

        if(Integer.parseInt(month_x) < 10){

            month_x = "0" + month_x;
        }
        if(Integer.parseInt(day_x) < 10){

            day_x  = "0" + day_x ;
        }
        infoData1 = "" + day_x + "/" + month_x + "/" + year_x;
        infoData2 = "" + year_x + month_x + day_x;

        mListener.onFragmentInteraction(infoData1, infoData2);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            mListener = (OnFragmentInteractionListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

}
