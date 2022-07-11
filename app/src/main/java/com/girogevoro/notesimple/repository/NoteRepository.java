package com.girogevoro.notesimple.repository;

import java.util.List;

public interface NoteRepository {
    void add(Note note);

    void remove(Note note);

    int length();

    Note get(int index);

    List<Note> getAll();
}
