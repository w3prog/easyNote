package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.w3prog.easynote.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by w3prog on 24.07.14.
 */
public class DatePickerFragment extends DialogFragment {

    private static final String TAG = "DatePickerFragment";

    private EditEventActivity editEventActivity;

    private Date mDate;

    public void setActivity(EditEventActivity myActivity) {
        editEventActivity = myActivity;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    private void sendResult(int ResultCode) {
        editEventActivity.setDateOfDialog(mDate);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG,"прошел в создание");
        Calendar calendar = Calendar.getInstance();
        if (mDate == null) mDate = new Date();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour =calendar.get(Calendar.HOUR);
        final int minute =calendar.get(Calendar.MINUTE);
        final int second =calendar.get(Calendar.SECOND);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date,null);
        Log.d(TAG,"Здесь");
        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_data_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day,hour,minute,second).getTime();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Отослал событие");
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

}
