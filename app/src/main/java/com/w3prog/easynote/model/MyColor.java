package com.w3prog.easynote.model;

import android.graphics.drawable.Drawable;

/**
 * Created by w3prog on 03.08.14.
 */
public class MyColor {

    public MyColor(int color, Drawable drawable ) {
        this.drawable = drawable;
        this.color = color;
    }

    private Drawable drawable;
    private int color;
    private String NameColor;

    public MyColor(Drawable drawable, int color, String nameColor) {
        this.drawable = drawable;
        this.color = color;
        NameColor = nameColor;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
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
