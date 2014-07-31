package com.w3prog.easynote.controller;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.Event;
import com.w3prog.easynote.model.EventCollection;

import java.util.ArrayList;


public class EventListActivity extends ListActivity {

    public static final String EXTRA_Id = "EventListActivity_ID";

    private ArrayList<Event> collectionEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionEvent = EventCollection.get(this).getEvents();

        EventAdapter groupAdapter = new EventAdapter(this);
        setListAdapter(groupAdapter);

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
