package com.w3prog.easynote.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        //Скоро уберу
        //geneCollection();
        //eventDateBase.deleteAllData();
        loadDataBase();
    }

    public ArrayList<Event> getSelectedEvents(int filter) {
        ArrayList<Event> colEvents = new ArrayList<Event>();

        for (Event item : Events)
            if (item.getGroupEvent().getId() == filter)
                colEvents.add(item);
        return colEvents;
    }

    //Данная функция очищает базу данных и вставляет туда данные из хранилища объектов
    //TODO можно будет реализовать эту функцию по другому или хотябы положить все действия
    //в транзакцию.
    public void updateBateBase(){
        //Todo сделать в будующем возможную обработку ошибок

        Log.d(TAG,"Начало обновления базы данных");
        (new Thread(new Runnable() {
            @Override
            public void run() {
                eventDateBase.deleteAllData();
                eventDateBase.insertGroupEventCollection(GroupEvents);
                eventDateBase.insertEventCollection(Events);
                Log.d(TAG,"Обновление базы данных завершено");
            }
        })).start();
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
        for (Event item:Events)
            if (item.getGroupEvent().equals(e)) deleteEvent(item);
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



    private void loadDataBase(){
        //Todo надо бы проверить на наличие ошибок при работе
        GroupEvents.addAll(eventDateBase.getGroupCollection());
        Events.addAll(eventDateBase.getEventCollection());
        Event.setIdentificator(maxIdEvents(Events));
        GroupEvent.setIdt(maxIdGroupEvent(GroupEvents));
    }

    public static EventCollection get(Context c){

        if (eventCollection == null)
            eventCollection = new EventCollection(c.getApplicationContext());
        return eventCollection;
    }
    public ArrayList<Event> getActiveEvents(){
        ArrayList<Event> colEvents = new ArrayList<Event>();

        for (Event item : Events)
            if (item.isActive())
                colEvents.add(item);
        return colEvents;
    }

    // В нормальной версии программы будет убрана.
    public void  geneCollection(){
        eventDateBase.deleteAllData();
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
        eventDateBase.insertGroupEventCollection(GroupEvents);
        eventDateBase.insertEventCollection(Events);
        Log.d(TAG,"Данные скинулись в базу данных");

        Events.clear();
        GroupEvents.clear();

    }

    //Получает максимальный ID
    //TODO выполнить тотже запрос через SQL
    public int maxIdEvents(ArrayList<Event> events){
        int MAX = 1;
        for (Event item:events){
            if(item.getId()>MAX) MAX=item.getId();
        }
        return MAX;
    }

    //Получает максимальный ID
    //TODO выполнить тотже запрос через SQL
    public int maxIdGroupEvent(ArrayList<GroupEvent> groupEvents){
        int MAX = 1;
        for (GroupEvent item:groupEvents){
            if(item.getId()>MAX) MAX=item.getId();
        }
        return MAX;
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
                    + EVENTS_DATE + " text,"
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

        // очищает полностью базу данных
        protected void deleteAllData(){
            Log.d(TAG,"Выполнение операций очистка базы данных");
            getWritableDatabase().delete("Groups", "", null);
            getWritableDatabase().delete("Events", "", null);

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

                Date newDate = null;
                try {
                    newDate = new SimpleDateFormat()
                            .parse(c.getString(c.getColumnIndex(EVENTS_DATE)));
                } catch (ParseException e) {
                    Log.d(TAG,"Ошибка Парсинга данных");
                    e.printStackTrace();
                }
                if( newDate == null ) newDate =new Date();

                event = new Event(
                        c.getInt(c.getColumnIndex(EVENTS_ID)),
                        c.getString(c.getColumnIndex(EVENTS_TITLE)),
                        c.getString(c.getColumnIndex(EVENTS_DESCRIPTION)),
                        eventDateBase.getGroupEvent(c.getInt(c.getColumnIndex(EVENTS_GROUPNAME))),
                        newDate,
                        c.getInt(c.getColumnIndex(EVENTS_REMEM)));

                Log.d(TAG,"Работа с курсором завершена");
                return event;
            } else {
                Log.d(TAG,"Курсор пустой");
                return null;
            }
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
            Cursor c = getWritableDatabase().rawQuery("Select "+EVENTS_ID+", " +
                    EVENTS_TITLE+", " +
                    EVENTS_DESCRIPTION+", " +
                    EVENTS_DATE+", " +
                    EVENTS_GROUPNAME+", " +
                    EVENTS_REMEM+", " +
                    EVENTS_ACTIVE+" from Events "+
                    "order by id",null);
            if (c.moveToFirst()){
                do {
                    Date newDate = null;
                    try {
                        newDate = new SimpleDateFormat()
                                .parse(c.getString(c.getColumnIndex(EVENTS_DATE)));
                    } catch (ParseException e) {
                        Log.d(TAG,"Ошибка Парсинга данных");
                        e.printStackTrace();
                    }
                    if( newDate == null ) newDate =new Date();
                    arrayList.add( new Event(
                            c.getInt(c.getColumnIndex(EVENTS_ID)),
                            c.getString(c.getColumnIndex(EVENTS_TITLE)),
                            c.getString(c.getColumnIndex(EVENTS_DESCRIPTION)),
                            eventDateBase.getGroupEvent(
                                    c.getInt(c.getColumnIndex(EVENTS_GROUPNAME))),
                            newDate,
                            c.getInt(c.getColumnIndex(EVENTS_REMEM))

                    ));
                    c.moveToNext();
                } while (c.isAfterLast() == false);
                Log.d(TAG,"Работа с курсором завершена getEventCollection");
            } else {
                Log.d(TAG,"Курсор c группами событий пустой");
            }


            return arrayList;

        }

        //Данный метод возращает коллекцию событий из базы данных
        public ArrayList<GroupEvent> getGroupCollection(){
            ArrayList<GroupEvent>  arrayList = new ArrayList<GroupEvent>();
            Cursor c = getWritableDatabase().rawQuery("Select id, " +
                    "nameGroup, " +
                    "description, " +
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
            ContentValues cv = new ContentValues();
            cv.put(EVENTS_ID, e.getId());
            cv.put(EVENTS_TITLE, e.getTitle() );
            cv.put(EVENTS_DESCRIPTION, e.getDescription());
            cv.put(EVENTS_DATE, e.getDate().toString());
            cv.put(EVENTS_GROUPNAME, e.getGroupEvent().getId());
            cv.put(EVENTS_REMEM, e.getRemem());
            cv.put(EVENTS_ACTIVE, e.isActive());
            long rowID = getWritableDatabase().insert("Events", null, cv);
        }

        //Данный метод добавляет событие в базу данных
        public void insertGroudEvent(GroupEvent e){

            ContentValues cv = new ContentValues();

            cv.put(GROUP_ID, e.getId());
            cv.put(GROUP_NAME, e.getTitle());
            cv.put(GROUP_DESCRIPTION, e.getDescription());
            cv.put(GROUP_COLOR, e.getColor());

            long rowID = getWritableDatabase().insert("Groups", null, cv);

        }

        public void insertEventCollection(ArrayList<Event> events){
            for(Event item:events){
                insertEvent(item);
            }
        }

        public void insertGroupEventCollection(ArrayList<GroupEvent> groupEvents){
            for(GroupEvent item:groupEvents){
                insertGroudEvent(item);
            }
        }

        //раньше считал что эти методы необходимы, но потом решил что они не нужны

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

        //Данный метод обновляет событие в базе данных по id
        public void updateEvent(int id, Event e){
            Log.d(TAG,"Подготовка значений Eve UPD");
            ContentValues cv = new ContentValues();

            cv.put(EVENTS_ID, e.getId());
            cv.put(EVENTS_TITLE, e.getTitle() );
            cv.put(EVENTS_DESCRIPTION, e.getDescription());
            cv.put(EVENTS_DATE, e.getDate().toString());
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
    }

}
