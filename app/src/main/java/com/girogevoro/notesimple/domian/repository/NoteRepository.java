package com.girogevoro.notesimple.domian.repository;

import java.util.List;

public interface NoteRepository {
    void add(Note note);

    void remove(Note note);

    int length();

    Note get(int index);

    List<Note> getAll();

    int position(Note note);
}
