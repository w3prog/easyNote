package com.w3prog.easynote.model;

import android.graphics.Color;

/**
 * Created by w3prog on 27.07.14.
 */
public class GroupEvent {

    private int id;
    private String Title;
    private int color;
    private String Description;

    public GroupEvent(int id, String title){
        this.id = id;
        this.Title = title;
        this.Description = "";
        this.color = Color.parseColor("#666666");
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
