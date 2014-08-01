package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.Event;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;


public class EditEventActivity extends Activity {

    public static final String EXTRA_ID = "EditEventActivity_ID";
    public static final String EXTRA_GROUP_ID = "EditEventActivity_GROUP_ID";
    public static final String EXTRA_TYPE = "EditEventActivity_TYPE";

    CheckBox activeCheckBox;
    EditText titleEditText;
    EditText deskriptionEditText;
    Button dateButton;
    Button remenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        Intent intent = getIntent();

        final Event event = EventCollection
                .get(getApplication())
                .getEvent(intent.getIntExtra(EXTRA_ID,1));

        if(intent.getBooleanExtra(EXTRA_TYPE,false)){
        GroupEvent groupEvent = EventCollection
                .get(getApplication())
                .getGroupEvent(intent.getIntExtra(EXTRA_GROUP_ID, 1));
        event.setGroupEvent(groupEvent);
        }

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

        dateButton = (Button)findViewById(R.id.button_ev_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO дописать выбор даты в программе
                // Скорее всего придется добавлять дополнительный виджет для ввода времени.
            }
        });

        remenButton = (Button)findViewById(R.id.button_ev_remember);
        remenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO дописать диалог выбор времени напоминания в программе
                //Реализую через диалог со списком
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
}
