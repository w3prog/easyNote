package com.w3prog.easynote.model;

import android.graphics.Color;
import android.util.Log;

public class GroupEvent {

    private static final String TAG ="GroupEvent";

    private int id;
    private String Title;
    private int color;
    private String Description;

    private static int idt = 0;

    public static void setIdt(int idt) {
        GroupEvent.idt = idt;
    }

    public GroupEvent() {

        this.id = idt++;
        Title = "";
        color =Color.parseColor("#FFD700");
        Description = "";
    }

    public GroupEvent(String mytitle){

        Log.d(TAG,"В конструтор попало");
        Log.d(TAG,Integer.toString(idt));

        this.id = idt++;
        Log.d(TAG,Integer.toString(idt));
        Log.d(TAG,mytitle);

        Title = mytitle;
        this.Description = "";
        this.color = Color.parseColor("#AAAAAA");
        Log.d(TAG,"Вышло");

    }

    public GroupEvent(int id, String title, String description, int color) {

        this.id = id;
        Title = title;
        Description = description;
        this.color = color;

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return Title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
