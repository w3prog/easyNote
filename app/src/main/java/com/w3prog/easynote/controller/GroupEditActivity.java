package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;
import com.w3prog.easynote.model.MyColor;

public class GroupEditActivity extends Activity {

    public static final String EXTRA_ID = "com.w3prog.easynote.controller.Group_ID";
    public static final String DIALOG_DATE = "date";
    private static final String TAG ="GroupEditActivity";

    private GroupEvent groupEvent;
    private EditText titleEditText;
    private EditText descriptionEditText;

    LinearLayout linearLayout;
    ImageView imageView;
    TextView colorNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group);


        Intent intent = getIntent();
        int i = intent.getIntExtra(EXTRA_ID,70);
        groupEvent = EventCollection.get(this).getGroupEvent(i);
        Log.d(TAG,"ID группы "+i);

        titleEditText = (EditText)findViewById(R.id.editText_gr_name);

        titleEditText.setText(groupEvent.getTitle().toString());


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

        linearLayout = (LinearLayout)findViewById(R.id.LLselect_Color);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartDialogColor();
            }
        });



        colorNameTextView = (TextView)findViewById(R.id.select_color_name);

        imageView = (ImageView)findViewById(R.id.my_image);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setColor(groupEvent.getColor());
        shapeDrawable.setIntrinsicWidth(40);
        shapeDrawable.setIntrinsicHeight(40);
        imageView.setBackgroundDrawable(shapeDrawable);
    }

    private void onStartDialogColor() {
        FragmentManager fm = getFragmentManager();
        ColorDialog dialog = new ColorDialog();
        Log.d(TAG,"Здесь");
        dialog.setMyActivity(this);
        Log.d(TAG,"прошел");
        dialog.show(fm, DIALOG_DATE);
    }


    public void setColorOfDialog(MyColor mColor) {
        groupEvent.setColor(mColor.getColor());
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setColor(mColor.getColor());
        shapeDrawable.setIntrinsicWidth(40);
        shapeDrawable.setIntrinsicHeight(40);
        imageView.setBackgroundDrawable(shapeDrawable);
        colorNameTextView.setText(mColor.getNameColor());
    }
}
