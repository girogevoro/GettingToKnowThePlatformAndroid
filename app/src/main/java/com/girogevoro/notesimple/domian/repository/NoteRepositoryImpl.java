package com.girogevoro.notesimple.domian.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    private static final String KEY_LIST = "KEY_LIST";
    private static final String NOTES = "NOTES";
    List<Note> mNoteList;

    private static NoteRepositoryImpl sNoteRepository;
    private static SharedPreferences sSharedPreferences;

    public static NoteRepositoryImpl getInstance(Context context) {
        if (sNoteRepository == null) {
            sSharedPreferences = context.getSharedPreferences(NOTES, Context.MODE_PRIVATE);
            sNoteRepository = new NoteRepositoryImpl();
        }
        return sNoteRepository;
    }

    private NoteRepositoryImpl() {
        initArrayList();
    }

    @Override
    public void add(Note note) {
        int pos = findPosition(note);
        if (pos != -1) {
            mNoteList.set(pos, note);
            saveArrayList();
            return;
        }
        mNoteList.add(note);
        saveArrayList();
    }

    private int findPosition(Note note) {
        for (int i = 0; i < mNoteList.size(); i++) {
            if (mNoteList.get(i).isEqual(note)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(Note note) {
        mNoteList.remove(findPosition(note));
        saveArrayList();
    }

    @Override
    public int length() {
        return mNoteList.size();
    }

    @Override
    public Note get(int index) {
        return mNoteList.get(index);
    }

    @Override
    public List<Note> getAll() {
        return mNoteList;
    }

    private void initArrayList() {
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        String key_list = sSharedPreferences.getString(KEY_LIST, "[]");
        mNoteList = new GsonBuilder()
                .create()
                .fromJson(key_list, type);
    }

    private void saveArrayList() {
        String s = new GsonBuilder()
                .create()
                .toJson(mNoteList);
        sSharedPreferences
                .edit()
                .putString(KEY_LIST, s)
                .apply();
    }

    @Override
    public int position(Note note) {
        return findPosition(note);

    }
}
