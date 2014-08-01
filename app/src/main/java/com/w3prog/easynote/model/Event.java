package com.w3prog.easynote.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by w3prog on 27.07.14.
 */
public class Event {

    private int id;
    private String title;
    private String description;
    private GroupEvent groupEvent;
    private Date date;
    private boolean active = true;
    private int remem = 0;

    private final static  int NEVER = 0 ;
    private final static int FIFTEEN = 1;
    private final static int HALFHOUR = 2;
    private final static int HOUR = 3;
    private final static int DAY = 4;

    public static void setIdentificator(int identificator) {
        Event.identificator = identificator;
    }

    private static int identificator = 0;


    public Event() {
        id=identificator++;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Event(String title,
                 GroupEvent groupEvent){
        identificator++;
        id = identificator;
        this.title = title;
        this.groupEvent = groupEvent;
        description = "";

        date = new Date();
    }


    public Event(int id,
            String title, String description,
                 GroupEvent groupEvent,
                 Date date,
                 int rem) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.groupEvent = groupEvent;
        this.date = date;
        this.remem = rem;
    }

    public Event(String title,
                 String description,
                 GroupEvent groupEvent) {
        this.id = identificator++;
        this.title = title;
        this.description = description;
        this.groupEvent = groupEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GroupEvent getGroupEvent() {
        return groupEvent;
    }

    public void setGroupEvent(GroupEvent groupEvent) {
        this.groupEvent = groupEvent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRemem() {
        return remem;
    }

    public void setRemem(int rem) {
        this.remem = rem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Date showTimeRemem(Date date,int a){

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        switch (a){
            case 0:
                return calendar.getTime();
            case 1:
                calendar.add(Calendar.MINUTE, -15);
                return calendar.getTime();
            case 2:
                calendar.add(Calendar.MINUTE,-30);
                return calendar.getTime();
            case 3:
                calendar.add(Calendar.HOUR,-1);
                return calendar.getTime();
            case 4:
                calendar.add(Calendar.DAY_OF_YEAR,-1);
                return calendar.getTime();
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return title + " " + date.toString();
    }
}
