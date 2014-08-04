package com.w3prog.easynote.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.easynote.R;

import java.util.ArrayList;
import java.util.List;


public class DialogSelectRememb extends DialogFragment {

    private static final String TAG = "DialogSelectRememb";

    ArrayList<String> myCollection;
    EditEventActivity myActivity;
    int remember_ID = 0 ;
    String nameRememb = "";

    public void setEditEventActivity(EditEventActivity myActivity) {
        this.myActivity = myActivity;
    }

    private void sendResult(int ResultCode) {

        myActivity.setRemembOfDialog(remember_ID, nameRememb);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_color_layout,null);

        myCollection = new ArrayList<String>();

        Resources resources = getResources();
        myCollection.add(resources.getString(R.string.never));
        myCollection.add(resources.getString(R.string.fifteen_minutes));
        myCollection.add(resources.getString(R.string.halfhour));
        myCollection.add(resources.getString(R.string.hour));
        myCollection.add(resources.getString(R.string.day));

        ListView listView = (ListView)v.findViewById(R.id.listView);
        MyStringAdapter Adapter = new MyStringAdapter(myCollection);
        listView.setAdapter(Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                remember_ID = position;
                Log.d(TAG,"Данные подошли " + remember_ID +
                        " " + nameRememb);
                nameRememb = myCollection.get(position);

                sendResult(Activity.RESULT_OK);
                dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Выберите время напоминания")
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

    class MyStringAdapter extends ArrayAdapter<String>{

        public MyStringAdapter(  List<String> objects) {
            super(getActivity(), 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1,null);
            }
            String str = getItem(position);

            TextView textView = (TextView)convertView.findViewById(android.R.id.text1);
            textView.setText(str);
            return convertView;

        }

    }
}
