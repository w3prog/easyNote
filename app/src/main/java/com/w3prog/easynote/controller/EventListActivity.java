package com.w3prog.easynote.controller;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.Event;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;

import java.util.ArrayList;


public class EventListActivity extends ListActivity {

    private static final String TAG = "EventListActivity";
    public static final String EXTRA_Id = "EventListActivity_ID";

    private ArrayList<Event> collectionEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        collectionEvent = EventCollection.get(this).getSelectedEvents(intent.getIntExtra(EXTRA_Id,1));

        EventAdapter groupAdapter = new EventAdapter(this);
        setListAdapter(groupAdapter);

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
                Log.d(TAG,"В приложение уже вошли!");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

            TextView TitleTextView = (TextView) convertView.findViewById(R.id.textView_fg_it_ev_TITLE);
            TitleTextView.setText(event.getTitle());
            TitleTextView.setBackgroundColor(event.getGroupEvent().getColor());


            TextView GroupTextView = (TextView) convertView.findViewById(R.id.textView_fg_it_ev_GROUP);
            GroupTextView.setText(event.getGroupEvent().getTitle());

            TextView DateTextView = (TextView) convertView.findViewById(R.id.textView_fg_it_ev_DATE);
            DateTextView.setText(event.getDate().toString());

            return convertView;
        }
    }
}
