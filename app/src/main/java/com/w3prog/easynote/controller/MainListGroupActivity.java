package com.w3prog.easynote.controller;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.w3prog.easynote.R;
import com.w3prog.easynote.model.EventCollection;
import com.w3prog.easynote.model.GroupEvent;

import java.util.ArrayList;


public class MainListGroupActivity extends ListActivity {

    ArrayList<GroupEvent> collectionGroupEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        collectionGroupEvent = EventCollection.get(this).getGroupEvents();

        GroupAdapter groupAdapter = new GroupAdapter(this);
        setListAdapter(groupAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, EventListActivity.class);
        //intent.putExtra(EventListActivity.EXTRA_Id,Eve)
        startActivity(intent);
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private class GroupAdapter extends ArrayAdapter<GroupEvent> {

        private GroupAdapter(Context context) {
            super(context,R.layout.item_group, collectionGroupEvent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null){
                convertView = getLayoutInflater()
                        .inflate(R.layout.item_group,null);
            }
            GroupEvent group = getItem(position);

            TextView GroupTextView = (TextView)convertView
                    .findViewById(R.id.textView_fg_it_gr_GROUP);
            GroupTextView.setText(group.getTitle());

            return convertView;
        }
    }
}
