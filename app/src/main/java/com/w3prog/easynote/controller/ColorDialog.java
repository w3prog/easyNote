package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.MyColor;

import java.util.ArrayList;

public class ColorDialog extends DialogFragment{

    GroupEditActivity groupEditActivity;
    MyColor mColor;

    private static final String TAG = "TimePickerFragment";

    public void setMyActivity(GroupEditActivity myActivity) {
        this.groupEditActivity = myActivity;
    }

    private void sendResult(int ResultCode) {
        groupEditActivity.setColorOfDialog(mColor);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_color_layout, null);

        ListView listView = (ListView)v.findViewById(R.id.listView);

        ArrayList<MyColor> myColorCollection = new ArrayList<MyColor>();

        myColorCollection.add( new MyColor(Color.parseColor("#0066bb"),"Синий"));
        myColorCollection.add( new MyColor(Color.parseColor("#dd0000"),"Красный"));
        myColorCollection.add( new MyColor(Color.parseColor("#FFD700"),"Золотой"));
        myColorCollection.add( new MyColor(Color.parseColor("#008800"),"Зеленый"));
        myColorCollection.add( new MyColor(Color.parseColor("#E0EEE0"),"Серый"));
        myColorCollection.add( new MyColor(Color.parseColor("#FFA500"),"Оранжевый"));
        myColorCollection.add( new MyColor(Color.parseColor("#bbaa00"),"Желтый"));
        myColorCollection.add( new MyColor(Color.parseColor("#FFFFFF"),"Белый"));
        myColorCollection.add( new MyColor(Color.parseColor("#000000"),"Черный"));

        final ColorAdapter colorAdapter = new ColorAdapter(myColorCollection);
        listView.setAdapter(colorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO метод должен возращать числовое значение цвета из диалога.
                mColor=colorAdapter.getItem(position);
                sendResult(Activity.RESULT_OK);
                
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Выберите цвет")
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    class ColorAdapter extends ArrayAdapter<MyColor>{

        @Override
        public MyColor getItem(int position) {
            return super.getItem(position);
        }

        public ColorAdapter(ArrayList<MyColor> myColors) {
            super(getActivity(), 0, myColors);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.dialog_color_item,null);
            }
            MyColor color = getItem(position);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            ShapeDrawable shapeDrawable = new ShapeDrawable();
            shapeDrawable.getPaint().setColor(color.getColor());
            shapeDrawable.setIntrinsicWidth(40);
            shapeDrawable.setIntrinsicHeight(40);
            imageView.setBackgroundDrawable(shapeDrawable);
            TextView textView = (TextView)convertView.findViewById(R.id.colorName);
            textView.setText(color.getNameColor());

            return convertView;

        }

    }




}
