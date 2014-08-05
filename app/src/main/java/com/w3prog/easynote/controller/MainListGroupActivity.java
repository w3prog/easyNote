package com.w3prog.easynote.controller;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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


public class MainListGroupActivity extends ListActivity {

    private static final String TAG = "MainListGroupActivity";
    ArrayList<GroupEvent> collectionGroupEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        collectionGroupEvent = EventCollection.get(this).getGroupEvents();

        GroupAdapter groupAdapter = new GroupAdapter(this);
        setListAdapter(groupAdapter);
        registerForContextMenu(getListView());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, EventListActivity.class);
        int ID_group = collectionGroupEvent.get(position).getId();
        intent.putExtra(EventListActivity.EXTRA_Id,ID_group);
        startActivity(intent);
    }




    @Override
    protected void onResume() {
        super.onResume();
        //обновиться
        ((GroupAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_new_groupCrime:
                GroupEvent group = new GroupEvent();
                Log.d(TAG,"В приложение уже вошли!");
                EventCollection.get(this).addGroupEvent(group);
                Intent i = new Intent(this, GroupEditActivity.class);
                i.putExtra(GroupEditActivity.EXTRA_ID, group.getId());
                Log.d(TAG,"В приложение уже вошли!");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    //Создание контекстного меню
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.group_event_context,menu);
    }

    @Override
    //Реакция на действия в контекстном меню
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo  menuInfo=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = menuInfo.position;
        GroupEvent groupEvent = ((GroupAdapter)getListAdapter())
                .getItem(position);

        switch (item.getItemId()){
            //Удаление элементы
            case R.id.delete_groupevent:
                EventCollection.get(this).deleteGroupEvent(groupEvent);
                ((GroupAdapter)getListAdapter()).notifyDataSetChanged();
                return true;
            //Редактирование элементы
            case R.id.edit_groupevent:
                Intent intent = new Intent(this, GroupEditActivity.class);
                int ID_event = groupEvent.getId();
                intent.putExtra(GroupEditActivity.EXTRA_ID,ID_event);
                startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

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
            GroupTextView.setBackgroundColor(group.getColor());
            return convertView;
        }
    }


}
