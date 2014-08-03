package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.w3prog.easynote.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by w3prog on 24.07.14.
 */
public class TimePickerFragment extends DialogFragment {

    private static final String TAG = "TimePickerFragment";


    private EditEventActivity editEventActivity;

    private Date mDate;

    public void setActivity(EditEventActivity myActivity) {
        this.editEventActivity = myActivity;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    private void sendResult(int ResultCode) {
        editEventActivity.setTimeOfDialog(mDate);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG,"прошел в создание");
        Calendar calendar = Calendar.getInstance();
        if (mDate == null) mDate = new Date();
        calendar.setTime(mDate);
        final int yearS = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour =calendar.get(Calendar.HOUR);
        int minute =calendar.get(Calendar.MINUTE);
        final int second =calendar.get(Calendar.SECOND);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time,null);
        Log.d(TAG,"Здесь");
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentMinute(minute);
        timePicker.setCurrentHour(hour);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mDate = new GregorianCalendar(yearS, month, day, hourOfDay, minute, second).getTime();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Время события")
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
