package com.w3prog.easynote.model;

import android.graphics.drawable.Drawable;


public class MyColor {

    public MyColor(int color) {
        this.color = color;
    }

    private int color;
    private String NameColor;

    public MyColor( int color, String nameColor) {
        this.color = color;
        NameColor = nameColor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getNameColor() {
        return NameColor;
    }

    public void setNameColor(String nameColor) {
        NameColor = nameColor;
    }

}
