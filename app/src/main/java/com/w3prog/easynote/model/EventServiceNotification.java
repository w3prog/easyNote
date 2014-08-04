package com.w3prog.easynote.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by w3prog on 31.07.14.
 */
public class EventServiceNotification extends Service {

    public static final String EXTRA_TITLE = "EventServiceNotification.title";
    public static final String EXTRA_DESCRIPTION = "EventServiceNotification.title";
    ArrayList<Event> events;
    private NotificationManager nm;
    private final int NOTIFICATION = 127;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = (String)intent.getStringExtra(EXTRA_TITLE);
        String description = (String)intent.getStringExtra(EXTRA_DESCRIPTION);

        showNotification(title,description);
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(String eventTitle,String Description){
        Notification.Builder nBuilder = new Notification.Builder(getApplicationContext());
        nBuilder
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setTicker("Напоминание о " + eventTitle)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentText(Description)
                .setContentTitle(eventTitle);

        Notification notification = nBuilder.getNotification();
        nm.notify(NOTIFICATION,notification);
    }
}
