package com.girogevoro.notesimple.domian.repository;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Note implements Parcelable {
    private String mName;
    private Date mDate;
    private String mDescription;
    private Integer mColor;
    private UUID id;

    public Note(String name, Date date, String description, Integer color) {
        mName = name;
        mDate = date;
        mDescription = description;
        mColor = color;
        id = UUID.randomUUID();
    }

    protected Note(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mDate = (Date) in.readSerializable();
        id = (UUID) in.readSerializable();
        if (in.readByte() == 0) {
            mColor = null;
        } else {
            mColor = in.readInt();
        }
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    public boolean isEqual(Note note){
        return id.equals(note.id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeSerializable(mDate);
        parcel.writeSerializable(id);
        if (mColor == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mColor);
        }
    }
}
