package com.w3prog.easynote.controller;

import android.app.Activity;
import android.os.Bundle;

import com.w3prog.easynote.R;


public class EditEventActivity extends Activity {

    public static final String EXTRA_ID ="EditEventActivity_ID";
    public static final String EXTRA_GROUP_ID = "EditEventActivity_GROUP_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

    }
}
