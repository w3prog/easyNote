package com.w3prog.easynote.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventCollection {

    private static final String TAG = "EventCollection";
    private static  EventCollection eventCollection;
    private Context context;
    private ArrayList<Event> Events;
    private ArrayList<GroupEvent> GroupEvents;
    private EventDateBase eventDateBase;


    private EventCollection(Context context) {
        this.context = context;
        Events = new ArrayList<Event>();
        GroupEvents = new ArrayList<GroupEvent>();
        Log.d(TAG,"Грузится коллекция");
        eventDateBase= new EventDateBase(context);
        geneCollection();
        geneCollectionInBD();
    }

    public ArrayList<Event> getSelectedEvents(int filter) {
        ArrayList<Event> colEvents = new ArrayList<Event>();

        for (Event item : Events)
            if (item.getGroupEvent().getId() == filter)
                colEvents.add(item);
        return colEvents;
    }

    public void addEvent(Event e){
        Events.add(e);
    }

    public void deleteEvent(Event e){
        Events.remove(e);
    }

    public void addGroupEvent(GroupEvent e){
        GroupEvents.add(e);
    }

    public void deleteGroupEvent(GroupEvent e){
        GroupEvents.remove(e);
    }

    public ArrayList<Event> getEvents(){
        return Events;
    }

    public ArrayList<GroupEvent> getGroupEvents() {
        return GroupEvents;
    }

    public Event getEvent(int id){
        for(Event item :Events)
            if (item.getId() == id)
                return item;
        return null;//Todo сделать исключительную ситуацию здесь
    }

    public GroupEvent getGroupEvent(int id){
        for(GroupEvent item :GroupEvents)
            if (item.getId() == id)
                return item;
        return null;//Todo сделать исключительную ситуацию здесь
    }

    public static EventCollection get(Context c){

        if (eventCollection == null)
            eventCollection = new EventCollection(c.getApplicationContext());
        return eventCollection;
    }

    private void onStart(){
        loadInBD();
    }

    private void loadInBD() {
        GroupEvents = eventDateBase.getGroupCollection();
        Events = eventDateBase.getEventCollection();
    }

    public ArrayList<Event> getActiveEvents(){
        ArrayList<Event> colEvents = new ArrayList<Event>();

        for (Event item : Events)
            if (item.isActive())
                colEvents.add(item);
        return colEvents;
    }

    public void  geneCollection(){
        Log.d(TAG,"Начало генерации");

        GroupEvent groupEvent = new GroupEvent("Домашние дела",
                Color.parseColor("#0066bb"));
        GroupEvents.add(groupEvent);
        Log.d(TAG,"Первый прошел");

        GroupEvent groupEvent1 = new GroupEvent("Работа",
                Color.parseColor("#dd0000"));
        GroupEvents.add(groupEvent1);


        GroupEvent groupEvent2 = new GroupEvent("Друзья",
                Color.parseColor("#FFD700"));
        GroupEvents.add(groupEvent2);

        GroupEvent groupEvent3 = new GroupEvent("Машина",
                Color.parseColor("#bbaa00"));
        GroupEvents.add(groupEvent3);

        GroupEvent groupEvent4 = new GroupEvent("Секрет",
                Color.parseColor("#000000"));
        GroupEvents.add(groupEvent4);

        Log.d(TAG,"Группы созданы");
        Event  event= new Event("Item " + 1, groupEvent);
        Log.d(TAG, groupEvent.toString());

        Log.d(TAG, "Group event есть");
        for (int i = 1; i < 100 ; i++ ) {
            Log.d(TAG, "Элемент " + i);
            switch (i%5 +1){
                case 1:
                    Events.add(new Event("Item " + i, groupEvent));
                    break;
                case 2:
                    Events.add(new Event("Item " + i, groupEvent1));
                    break;
                case 3:
                    Events.add(new Event("Item " + i, groupEvent2));
                    break;
                case 4:
                    Events.add(new Event("Item " + i, groupEvent3));
                    break;
                default:
                    Events.add(new Event("Item " + i, groupEvent4));
                    break;
            }
        }
        Log.d(TAG,"Группы создались");
        Event.setIdentificator(Events.size());
        GroupEvent.setIdt(GroupEvents.size());
    }

    public void geneCollectionInBD(){

        Log.d(TAG, "Начало ввесения строк в GROUPS");
        GroupEvent groupEvent = new GroupEvent( "Группа событий");


        Log.d(TAG, "Начало ввесения строк в EVENTS");
        Event  event = new Event(10000,"Item " + 1, "Не значительное событие",groupEvent,
                new Date(),2);
        Log.d(TAG, event.toStringFull());

        eventDateBase.insertGroudEvent(groupEvent);
        eventDateBase.insertEvent(event);
        Log.d(TAG, "Вывести её содержимое");
        Event newEvent = eventDateBase.getEvent(event.getId());

        Log.d(TAG, Integer.toString(newEvent.getId()));
        Log.d(TAG, newEvent.getTitle());
        Log.d(TAG, newEvent.getDescription());
        Log.d(TAG, newEvent.getGroupEvent().getTitle());
        Log.d(TAG, newEvent.getDate().toString());
        Log.d(TAG, Integer.toString(newEvent.getRemem()));
        Log.d(TAG, Boolean.toString(newEvent.isActive()));


        Log.d(TAG, newEvent.toStringFull());
    }


    public class EventDateBase extends SQLiteOpenHelper {

        private static final String NAME_DATA_BASE = "EventDateBase";
        private static final int VERSION_DATA_BASE = 2;
        private static final String TAG =  "EventDateBase";

        //Названия колонок
        private static final String EVENTS_ID ="id";
        private static final String EVENTS_TITLE ="title";
        private static final String EVENTS_DESCRIPTION ="description";
        private static final String EVENTS_GROUPNAME ="groupNAME";
        private static final String EVENTS_DATE ="date";
        private static final String EVENTS_REMEM ="remem";
        private static final String EVENTS_ACTIVE ="active";

        private static final String GROUP_ID ="id";
        private static final String GROUP_NAME ="nameGroup";
        private static final String GROUP_DESCRIPTION ="description";
        private static final String GROUP_COLOR ="color";

        public EventDateBase(Context context) {
            super(context, NAME_DATA_BASE, null, VERSION_DATA_BASE);
        }

        //Данные метод создает начальную базу данных если нужно
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "--- onCreate database ---");
            db.execSQL("create table Events ("
                    + EVENTS_ID + " integer primary key,"
                    + EVENTS_TITLE + " text,"
                    + EVENTS_DESCRIPTION + " text,"
                    + EVENTS_DATE + " integer,"
                    + EVENTS_GROUPNAME+" integer,"
                    + EVENTS_REMEM+"  integer," +
                      EVENTS_ACTIVE +" integer" + ");");

            db.execSQL("create table Groups ("
                     + GROUP_ID + " integer primary key,"
                     + GROUP_NAME + " text ,"
                     + GROUP_DESCRIPTION + " text,"
                     + GROUP_COLOR + " integer);");

            Log.d(TAG,"База данных успешно создана");
        }

        //Данный метод должен обновлять старую версию базы до новой
        // пока в нем нет особой необходимости
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //надеюсь не понадобится
        }

        // Данный метод совершает запрос к базе данных на получение одного события по его ID
        public Event getEvent (int id){
            Log.d(TAG,"Начало создания Курсора getEvent");
            Cursor c = getWritableDatabase().query("Events",null,
                    EVENTS_ID+"="+id,null,
                    null,null,null);
            Event event;
            Log.d(TAG,"Работа с курсором");


            if (c.moveToFirst()){
                event = new Event(
                        c.getInt(c.getColumnIndex(EVENTS_ID)),
                        c.getString(c.getColumnIndex(EVENTS_TITLE)),
                        c.getString(c.getColumnIndex(EVENTS_DESCRIPTION)),
                        eventDateBase.getGroupEvent(c.getInt(c.getColumnIndex(EVENTS_GROUPNAME))),
                        new Date(c.getInt(c.getColumnIndex(EVENTS_DATE))),
                        c.getInt(c.getColumnIndex(EVENTS_REMEM)));

                Log.d(TAG,event.getDate().toString());
                Log.d(TAG,"Работа с курсором завершена");
                return event;
            } else {
                Log.d(TAG,"Курсор пустой");
                return null;
            }
        }

        // очищает полностью базу данных
        protected void deleteAllData(){
            Log.d(TAG,"Выполнение операций очистка базы данных");
            getWritableDatabase().delete("Groups", GROUP_ID + " = *", null);
            getWritableDatabase().delete("Events", "id = *", null);

        }

        //Данный метод возращает группу по ID
        public GroupEvent getGroupEvent (int id){
            Log.d(TAG,"Начало создания Курсора");
            Cursor c = getWritableDatabase().query("Groups",
                    null,
                    GROUP_ID+"="+id,
                    null,null,null,null);
            GroupEvent groupEvent;
            Log.d(TAG,"Работа с курсором");

            if (c.moveToFirst()){
                groupEvent = new GroupEvent(
                        c.getInt(c.getColumnIndex(GROUP_ID)),
                        c.getString(c.getColumnIndex(GROUP_NAME)),
                        c.getString(c.getColumnIndex(GROUP_DESCRIPTION)),
                        c.getInt(c.getColumnIndex(GROUP_COLOR))
                );
                Log.d(TAG,"Работа с курсором завершена");
                return groupEvent;
            } else {
                Log.d(TAG,"Курсор пустой");
                return null;
            }
        }

        //Данный метод созращает коллекцию событий для приложений
        public ArrayList<Event> getEventCollection(){
            ArrayList<Event>  arrayList = new ArrayList<Event>();

            Cursor c = getWritableDatabase().rawQuery("Select id," +
                    "title," +
                    "description," +
                    "date," +
                    "groupName," +
                    "reminder from Events"+
                    "order by id",null);


            //Todo сделать возможным правильные ввод данный



            return arrayList;
        }

        //Данный метод возразает коллекцию событий из базы данных
        public ArrayList<GroupEvent> getGroupCollection(){
            ArrayList<GroupEvent>  arrayList = new ArrayList<GroupEvent>();
            Cursor c = getWritableDatabase().rawQuery("Select name," +
                    "description," +
                    "color from Groups order by id",null);

            if (c.moveToFirst()){
                do {
                    arrayList.add( new GroupEvent(
                            c.getInt(c.getColumnIndex(GROUP_ID)),
                            c.getString(c.getColumnIndex(GROUP_NAME)),
                            c.getString(c.getColumnIndex(GROUP_DESCRIPTION)),
                            c.getInt(c.getColumnIndex(GROUP_COLOR))
                    ));
                    c.moveToNext();
                } while (c.isAfterLast() == false);

                Log.d(TAG,"Работа с курсором завершена");
            } else {
                Log.d(TAG,"Курсор c группами событий пустой");
            }


            return arrayList;
        }

        //данный метод добавляет событие в базу данных
        public void insertEvent(Event e){
            Log.d(TAG,"Подготовка значений Eve INT");


            ContentValues cv = new ContentValues();
            cv.put(EVENTS_ID, e.getId());
            cv.put(EVENTS_TITLE, e.getTitle() );
            cv.put(EVENTS_DESCRIPTION, e.getDescription());
            cv.put(EVENTS_DATE, e.getDate().getDate());
            cv.put(EVENTS_GROUPNAME, e.getGroupEvent().getId());
            cv.put(EVENTS_REMEM, e.getRemem());
            cv.put(EVENTS_ACTIVE, e.isActive());
            Log.d(TAG, "Подготовка значений выполнение запроса EVE INT");
            long rowID = getWritableDatabase().insert("Events", null, cv);
        }

        //Данный метод обновляет событие в базе данных по id
        public void updateEvent(int id, Event e){
            Log.d(TAG,"Подготовка значений Eve UPD");
            ContentValues cv = new ContentValues();

            cv.put(EVENTS_ID, e.getId());
            cv.put(EVENTS_TITLE, e.getTitle() );
            cv.put(EVENTS_DESCRIPTION, e.getDescription());
            cv.put(EVENTS_DATE, e.getDate().getTime());
            cv.put(EVENTS_GROUPNAME, e.getGroupEvent().getId());
            cv.put(EVENTS_REMEM, e.getRemem());
            cv.put(EVENTS_ACTIVE, e.isActive());

            Log.d(TAG, "Выполнение запроса EVE UPD");
            getWritableDatabase().update("Events", cv, EVENTS_ID + " = ?",
                    new String[]{Integer.toString(id)});
            Log.d(TAG,"Завершение запроса Eve UPD");
        }

        //Данный метод удаляет событие по id
        public void deleteEvent(int id){
            int delCount = getWritableDatabase().delete("Events", "id = " + id, null );
        }

        //Данный метод добавляет событие в базу данных
        public void insertGroudEvent(GroupEvent e){
            Log.d(TAG,"Подготовка значений");
            ContentValues cv = new ContentValues();

            cv.put(GROUP_ID, e.getId());
            cv.put(GROUP_NAME, e.getTitle());
            cv.put(GROUP_DESCRIPTION, e.getDescription());
            cv.put(GROUP_COLOR, e.getColor());
            Log.d(TAG,"Выполнение операций INS");
            long rowID = getWritableDatabase().insert("Groups", null, cv);

        }

        //Обновление Группы событий
        public void updateGroupEvent(int id, GroupEvent e ){
            Log.d(TAG,"Подготовка значений");
            ContentValues cv = new ContentValues();

            cv.put(GROUP_ID, e.getId());
            cv.put(GROUP_NAME, e.getTitle());
            cv.put(GROUP_DESCRIPTION, e.getDescription());
            cv.put(GROUP_COLOR, e.getColor());
            Log.d(TAG,"Выполнение операций UPD");
            int updCount = getWritableDatabase().update("Groups", cv, GROUP_ID + " = ?",
                    new String[] { Integer.toString(id) });
        }

        //Удаление группы событий
        public void deleteGroupEvent( int id){
            Log.d(TAG,"Выполнение операций DEL для строки с id равным " + id);
            getWritableDatabase().delete("Groups", GROUP_ID + " = " + id, null);
        }
    }

}
