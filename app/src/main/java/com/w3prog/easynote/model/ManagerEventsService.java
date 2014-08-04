package com.w3prog.easynote.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Date;

public class ManagerEventsService extends Service {
    AlarmManager alarmManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        alarmManager = (AlarmManager)getApplicationContext()
                .getSystemService(ALARM_SERVICE);

        for (Event item:EventCollection.
                get(getApplicationContext()).getEvents())
            if (item.isActive()&&
                    testDate(item.getDate())) eventArrayList.add(item);

        ArrayList<Date> coldate =new ArrayList<Date>();

        for(Event item:eventArrayList){
            coldate.add(item.showTimeRemem(item.getDate(),
                    item.getRemem()));
        }
        int i = 0;
        for (Event item : eventArrayList){
            Intent eventIntent = new Intent(this,EventServiceNotification.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
            alarmManager.set(AlarmManager.RTC,coldate.get(i).getDate(),pendingIntent);
            i++;
        }

        //TODO еще нужно решить проблему с обновлением событий в объекте


        return super.onStartCommand(intent, flags, startId);
    }

    private boolean testDate(Date date) {
        //TODO определить логику выбора даты
        return true;
    }
}
