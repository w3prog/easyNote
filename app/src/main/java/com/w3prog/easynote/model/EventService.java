package com.w3prog.easynote.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Date;
import java.util.ArrayList;


public class EventService extends Service {

    ArrayList<Event> events;
    private NotificationManager nm;
    private final int NOTIFICATION = 127;
    private AlarmManager alarmManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ArrayList<Event> colEvents = EventCollection.get(getApplication()).getActiveEvents();
        ArrayList<Date> coldate =new ArrayList<Date>();
        for(Event item:colEvents){
             coldate.add(item.showTimeRemem(item.getDate(),
             item.getRemem()));
        }
        alarmManager = (AlarmManager)getApplicationContext()
                .getSystemService(ALARM_SERVICE);
        //alarmManager.set();

        //TODO
        //
        //  Получить заявку на оповещения с датой
        //      Вопрос где они будут храниться и как их обрабатывать
        //  Ожидать времени для выполнения заявки
        //      AlarmManager.set();
        //

        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(Event event){
        Notification.Builder nBuilder = new Notification.Builder(getApplicationContext());
        nBuilder
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setTicker("Напоминание о " + event.getTitle())
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentText(event.getDescription())
                .setContentTitle(event.getTitle());

        Notification notification = nBuilder.build();
        nm.notify(NOTIFICATION,notification);
    }
}
