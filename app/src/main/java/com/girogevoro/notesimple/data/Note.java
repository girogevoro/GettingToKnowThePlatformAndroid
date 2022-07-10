package com.girogevoro.notesimple.data;

import android.graphics.Color;

import java.sql.Time;

public class Note {
    private String mName;
    private Time mTime;
    private String mDescription;
    private Color mColor;

    public Note(String name, Time time, String description, Color color) {
        mName = name;
        mTime = time;
        mDescription = description;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Time getTime() {
        return mTime;
    }

    public void setTime(Time time) {
        mTime = time;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color color) {
        mColor = color;
    }
}
