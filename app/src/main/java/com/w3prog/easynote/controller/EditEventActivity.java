package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.Event;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;
import com.w3prog.easynote.model.MyColor;

import java.util.Calendar;
import java.util.Date;


public class EditEventActivity extends Activity {

    public static final String DIALOG_DATE = "date";
    public static final String EXTRA_ID = "EditEventActivity_ID";
    public static final String EXTRA_GROUP_ID = "EditEventActivity_GROUP_ID";
    public static final String EXTRA_TYPE = "EditEventActivity_TYPE";

    CheckBox activeCheckBox;
    EditText titleEditText;
    EditText deskriptionEditText;
    Button buttonDate;
    Button buttonTime;
    Button remenButton;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        Intent intent = getIntent();


        event = EventCollection
                .get(getApplication())
                .getEvent(intent.getIntExtra(EXTRA_ID,1));
        if (event.getDate() == null ) event.setDate(new Date());
        if(intent.getBooleanExtra(EXTRA_TYPE,false)){
        GroupEvent groupEvent = EventCollection
                .get(getApplication())
                .getGroupEvent(intent.getIntExtra(EXTRA_GROUP_ID, 1));
        event.setGroupEvent(groupEvent);
        }

        buttonDate = (Button)findViewById(R.id.button_date);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstartDialogDate();
            }
        });

        buttonTime = (Button)findViewById(R.id.button_time);
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstartDialogTime();
            }
        });

        titleEditText = (EditText)findViewById(R.id.editText_ev_title);
        titleEditText.setText(event.getTitle());
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                event.setTitle(titleEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        deskriptionEditText = (EditText)findViewById(R.id.editText_description_fg_ev);
        deskriptionEditText.setText(event.getDescription());
        deskriptionEditText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                event.setDescription(deskriptionEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        remenButton = (Button)findViewById(R.id.button_ev_remember);
        remenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startDialogRemem();
            }
        });

        activeCheckBox = (CheckBox)findViewById(R.id.Active_checkBox);
        activeCheckBox.setChecked(event.isActive());
        activeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                event.setActive(activeCheckBox.isChecked());
            }
        });


    }

    void startDialogRemem() {
        FragmentManager fm = getFragmentManager();
        DialogSelectRememb dialog = new DialogSelectRememb();
        dialog.setEditEventActivity(this);
        dialog.show(fm, DIALOG_DATE);
    }

    private void onstartDialogTime() {
        FragmentManager fm = getFragmentManager();
        TimePickerDialog dialog = new TimePickerDialog();
        dialog.setActivity(this);
        dialog.setDate(event.getDate());
        dialog.show(fm, DIALOG_DATE);
    }

    private void onstartDialogDate() {
        FragmentManager fm = getFragmentManager();
        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setActivity(this);
        dialog.setDate(event.getDate());
        dialog.show(fm, DIALOG_DATE);
    }

    public void setTimeOfDialog(Date mDate) {
        writeTime(mDate);
        event.setDate(mDate);
    }

    public void setDateOfDialog(Date mDate) {
        writeDate(mDate);
        event.setDate(mDate);
    }


    private void writeDate(Date date){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(calendar.YEAR);
        String month = toMinth(calendar.get(calendar.MONTH));
        int days = calendar.get(calendar.DAY_OF_MONTH);

        String Sdate ="" +days + " " + month + " " + year;
        buttonDate.setText(Sdate);
    }

    private void writeTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int Hour = calendar.get(calendar.HOUR_OF_DAY);
        int minute = calendar.get(calendar.MINUTE);
        buttonTime.setText("" + Hour + " : " + minute);
    }

    private String toMinth(int s) {

        switch (s){
            case 0:
                return "Января";
            case 1:
                return "Февраля";
            case 2:
                return "Марта";
            case 3:
                return "Апреля";
            case 4:
                return "Мая";
            case 5:
                return "Июня";
            case 6:
                return "Июля";
            case 7:
                return "Августа";
            case 8:
                return "Сентября";
            case 9:
                return "Октября";
            case 10:
                return "Ноября";
            case 11:
                return "Декабря";
            default:
                return null;
        }
    }


    public void setRemembOfDialog(int remember_id, String nameRememb) {
        remenButton.setText(nameRememb);
        event.setRemem(remember_id);
    }
}
