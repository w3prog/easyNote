package com.w3prog.easynote.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ManagerEventsService extends Service {
    AlarmManager alarmManager;

    public static final String COMMAND_ADD =
            "w3prog.easynote.model.ManagerEventsService.ADD";

    public static final String COMMAND_UPD =
            "w3prog.easynote.model.ManagerEventsService.UPDATE";

    public static final String COMMAND_DELETE =
            "w3prog.easynote.model.ManagerEventsService.DELETE";

    public static final String COMMAND_START =
            "w3prog.easynote.model.ManagerEventsService.START";

    public static final String COMMAND = "COMMAND";

    public static final String ID_EVENT =
            "w3prog.easynote.model.ManagerEventsService.ID_EVENT";

    private static ArrayList<Integer> ListID = new ArrayList<Integer>();

    HashMap<Integer,PendingIntent> pendingsIntents;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         //определяет тип команды полученой от события
        if(intent.getStringExtra(COMMAND) == COMMAND_START) stantNew(intent);
        if(intent.getStringExtra(COMMAND) == COMMAND_ADD) stantAdd(intent);
        if(intent.getStringExtra(COMMAND) == COMMAND_UPD) stantUpd(intent);
        if(intent.getStringExtra(COMMAND) == COMMAND_DELETE) stantDel(intent);

        //TODO еще нужно решить проблему с обновлением событий в объекте


        return super.onStartCommand(intent, flags, startId);
    }

    private void stantNew(Intent intent) {
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        alarmManager = (AlarmManager)getApplicationContext()
                .getSystemService(ALARM_SERVICE);

        for (Event item:EventCollection.
                get(getApplicationContext()).getEvents())
            if (item.isActive()&&
                    testDate(item.getDate())) eventArrayList.add(item);

        ArrayList<Date> coldate = new ArrayList<Date>();

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
    }

    private void stantAdd(Intent intent) {
        int id = intent.getIntExtra(ID_EVENT,1);
        Event event = EventCollection.get(getApplication()).getEvent(id);
        if (event.isActive() && testDate(event.getDate()))return;

        Intent eventIntent = new Intent(this,EventServiceNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,eventIntent,0);
        alarmManager.set(AlarmManager.RTC,
                event.showTimeRemem(event.getDate(), event.getRemem()).getDate()
                ,pendingIntent);

    }

    private void stantUpd(Intent intent) {
        int id = intent.getIntExtra(ID_EVENT,1);
        Event event = EventCollection.get(getApplication()).getEvent(id);
        //alarmManager.cancel();

    }

    private void stantDel(Intent intent) {
        int id = intent.getIntExtra(ID_EVENT,1);




    }

    private boolean testDate(Date date) {
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(nowDate.getDate());
        nowDate = calendar.getTime();
        calendar.add(calendar.DAY_OF_MONTH,1);
        Date tomarrowDate = calendar.getTime();
        if(nowDate.before(date) && nowDate.after(tomarrowDate)){

            return true;
        } else {
            return false;
        }
    }

}
