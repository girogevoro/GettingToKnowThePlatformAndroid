package com.girogevoro.notesimple.UI.fragment;

import com.girogevoro.notesimple.domian.repository.Note;

public interface INoteListFragment {
    void setNote(Note note);
    void editNote(Note note);
    void addNote(Note note);
}
