package com.girogevoro.notesimple.repository;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    List<Note> mNoteList;

    private static NoteRepositoryImpl sNoteRepository;

    public static NoteRepositoryImpl getInstance() {
        if (sNoteRepository == null) {
            sNoteRepository = new NoteRepositoryImpl();
        }
        return sNoteRepository;
    }

    private NoteRepositoryImpl() {
        mNoteList = new ArrayList<>();
    }

    @Override
    public void add(Note note) {
        mNoteList.add(note);
    }

    @Override
    public void remove(Note note) {
        mNoteList.remove(note);
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
}
