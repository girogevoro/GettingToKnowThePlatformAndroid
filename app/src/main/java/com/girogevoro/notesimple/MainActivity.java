package com.girogevoro.notesimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import com.girogevoro.notesimple.repository.Note;
import com.girogevoro.notesimple.repository.NoteRepositoryImpl;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements INoteListFragment {

    static {
        Calendar c = Calendar.getInstance();
        c.set(2022, 3, 4);
        NoteRepositoryImpl.getInstance().add(new Note("note1", c.getTime(), "dest 1", Color.GREEN));
        c.set(2022, 3, 5);
        NoteRepositoryImpl.getInstance().add(new Note("note2", c.getTime(), "dest 2", Color.YELLOW
        ));
        c.set(2022, 3, 6);
        NoteRepositoryImpl.getInstance().add(new Note("note3", c.getTime(), "dest 3", Color.MAGENTA));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_container, NoteListFragment.newInstance(this))
                .commit();
    }

    @Override
    public void setNote(Integer index) {
        if (isPortrait()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.list_container, NoteInfoFragment.newInstance(index))
                    .addToBackStack("")
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_info, NoteInfoFragment.newInstance(index))
                    .commit();
        }
    }

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}