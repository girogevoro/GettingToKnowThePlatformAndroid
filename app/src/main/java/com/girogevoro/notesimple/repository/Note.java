package com.girogevoro.notesimple.repository;


import java.util.Date;

public class Note {
    private String mName;
    private Date mDate;
    private String mDescription;
    private Integer mColor;

    public Note(String name, Date date, String description, Integer color) {
        mName = name;
        mDate = date;
        mDescription = description;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Integer getColor() {
        return mColor;
    }

    public void setColor(Integer color) {
        mColor = color;
    }
}
