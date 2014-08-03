package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

/**
 * Created by w3prog on 02.08.14.
 */
public class DialogColor extends DialogFragment{



    GroupEditActivity groupEditActivity;
    MyColor mColor;

    public void setMyActivity(GroupEditActivity myActivity) {
        this.groupEditActivity = myActivity;
    }

    public void setColor(MyColor mColor) {
        this.mColor = mColor;
    }

    private void sendResult(int ResultCode) {
        groupEditActivity.setColorOfDialog(mColor);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_color_layout, null);

        ListView listView = (ListView)v.findViewById(R.id.listView);

        listView.getAdapter();

        Resources resources = getResources();

        Drawable drawable1 = resources.getDrawable(R.drawable.blue_box);
        MyColor myColor1 = new MyColor(drawable1,
                Color.parseColor("#0066bb"),
                resources.getString(R.string.Color_Blue));
        Drawable drawable2 = resources.getDrawable(R.drawable.red_box);
        MyColor myColor2 = new MyColor(drawable2,
                Color.parseColor("#dd0000"),
                resources.getString(R.string.Color_Red));

        Drawable drawable3 = resources.getDrawable(R.drawable.gold_box);
        MyColor myColor3 = new MyColor(drawable3,
                Color.parseColor("#FFD700"),
                resources.getString(R.string.Color_Gold));

        Drawable drawable4 = resources.getDrawable(R.drawable.green_box);
        MyColor myColor4 = new MyColor(drawable4,
                Color.parseColor("#008800"),
                resources.getString(R.string.Color_Green));

        Drawable drawable5 = resources.getDrawable(R.drawable.honeydew_box);
        MyColor myColor5 = new MyColor(drawable5,
                Color.parseColor("#E0EEE0"),
                resources.getString(R.string.Color_Honeydew));

        Drawable drawable6 = resources.getDrawable(R.drawable.orange_box);
        MyColor myColor6 = new MyColor(drawable6,
                Color.parseColor("#FFA500"),
                resources.getString(R.string.Color_Orange));

        Drawable drawable7 = resources.getDrawable(R.drawable.yellow_box);
        MyColor myColor7 = new MyColor(drawable7,
                Color.parseColor("#bbaa00"),
                resources.getString(R.string.Color_Yellow));

        ArrayList<MyColor> myColorCollection = new ArrayList<MyColor>();

        myColorCollection.add(myColor1);
        myColorCollection.add(myColor2);
        myColorCollection.add(myColor3);
        myColorCollection.add(myColor4);
        myColorCollection.add(myColor5);
        myColorCollection.add(myColor6);
        myColorCollection.add(myColor7);

        final ColorAdapter colorAdapter = new ColorAdapter(myColorCollection);
        listView.setAdapter(colorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO метод должен возращать числовое значение цвета из диалога.
                mColor=colorAdapter.getItem(position);
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
            imageView.setImageDrawable(color.getDrawable());

            TextView textView = (TextView)convertView.findViewById(R.id.colorName);
            textView.setText(color.getNameColor());

            return convertView;

        }

    }




}
