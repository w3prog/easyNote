package com.w3prog.easynote.controller;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.Event;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class EventListActivity extends ListActivity {

    private static final String TAG = "EventListActivity";
    public static final String EXTRA_Id = "EventListActivity_ID";
    private int ID_group = 1 ;
    private ArrayList<Event> collectionEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ID_group = intent.getIntExtra(EXTRA_Id,1);
        collectionEvent = EventCollection.get(this)
                .getSelectedEvents(ID_group);
        EventAdapter eventAdapter = new EventAdapter(this);
        setListAdapter(eventAdapter);

        registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_new_groupCrime:
                Event event = new Event();
                Intent intent = getIntent();
                int idGroup = intent.getIntExtra(EXTRA_Id,1);

                Log.d(TAG, "В приложение уже вошли!");
                EventCollection.get(this).addEvent(event);
                Intent i = new Intent(this, EditEventActivity.class);
                i.putExtra(EditEventActivity.EXTRA_ID, event.getId());
                i.putExtra(EditEventActivity.EXTRA_GROUP_ID, idGroup);
                i.putExtra(EditEventActivity.EXTRA_TYPE,true);
                Log.d(TAG,"В приложение уже вошли!");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, EditEventActivity.class);
        int ID_event = collectionEvent.get(position).getId();

        intent.putExtra(EditEventActivity.EXTRA_ID,ID_event);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //обновление данных на листе
        collectionEvent = EventCollection.get(this)
                .getSelectedEvents(ID_group);
        EventAdapter eventAdapter = new EventAdapter(this);
        setListAdapter(eventAdapter);

        ((EventAdapter)getListAdapter()).notifyDataSetChanged();
    }


    @Override
    //Создание контекстного меню
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.event_list_context,menu);
    }

    @Override
    //Реакция на действия в контекстном меню
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo  menuInfo=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = menuInfo.position;
        Event event = ((EventAdapter)getListAdapter())
                .getItem(position);

        switch (item.getItemId()){
            //Удаление элементы
            case R.id.delete_groupevent:
                EventCollection.get(this).deleteEvent(event);
                onResume();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private class EventAdapter extends ArrayAdapter<Event> {

        private EventAdapter(Context context) {
            super(context, 0, collectionEvent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.item_event, null);
            }
            Event event = getItem(position);

            TextView TitleTextView = (TextView) convertView
                    .findViewById(R.id.textView_fg_it_ev_TITLE);
            TitleTextView.setText(event.getTitle());
            TitleTextView.setBackgroundColor(event.getGroupEvent().getColor());
            //зачеркивание если не активно
            if (!event.isActive())
                TitleTextView.setPaintFlags(TitleTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            TextView GroupTextView = (TextView) convertView
                    .findViewById(R.id.textView_fg_it_ev_GROUP);
            GroupTextView.setText(event.getGroupEvent().getTitle());

            TextView DateTextView = (TextView) convertView
                    .findViewById(R.id.textView_fg_it_ev_DATE);
            DateTextView.setText(writeDate(event.getDate()));

            return convertView;
        }
    }


    private String writeDate(Date date){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(calendar.YEAR);
        String month = toMinth(calendar.get(calendar.MONTH));
        int days = calendar.get(calendar.DAY_OF_MONTH);
        int Hour = calendar.get(calendar.HOUR_OF_DAY);
        int minute = calendar.get(calendar.MINUTE);

        return "" + Hour + " : " + minute+"  "+days + " " + month + " " + year ;
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
}
