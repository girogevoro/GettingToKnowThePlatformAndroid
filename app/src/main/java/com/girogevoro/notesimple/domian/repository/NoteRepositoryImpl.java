package com.girogevoro.notesimple.domian.repository;

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
        int pos = findPosition(note);
        if (pos != -1) {
            mNoteList.set(pos, note);
            return;
        }
        mNoteList.add(note);
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

    @Override
    public int position(Note note) {
        return findPosition(note);

    }
}
