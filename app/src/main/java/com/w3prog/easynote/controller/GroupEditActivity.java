package com.w3prog.easynote.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;
import com.w3prog.easynote.model.MyColor;

public class GroupEditActivity extends Activity {

    public static final String EXTRA_ID = "com.w3prog.easynote.controller.Group_ID";

    private static final String TAG ="GroupEditActivity";

    private GroupEvent groupEvent;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button colorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group);

        Log.d(TAG,"onCreate");
        Intent intent = getIntent();
        int i = intent.getIntExtra(EXTRA_ID,0);
        groupEvent = EventCollection.get(this).getGroupEvent(i);
        Log.d(TAG,"onCreate");

        Log.d(TAG,groupEvent.getTitle() + "11234");

        titleEditText = (EditText)findViewById(R.id.editText_gr_name);
        Log.d(TAG,"onCreate");
        titleEditText.setText(groupEvent.getTitle().toString());
        Log.d(TAG,"onCreate");

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupEvent.setTitle(titleEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.d(TAG,"onCreate");

        descriptionEditText = (EditText)findViewById(R.id.editText_description_gr);
        descriptionEditText.setText(groupEvent.getDescription().toString());
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupEvent.setDescription(descriptionEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    public void setColorOfDialog(MyColor mColor) {

    }
}
