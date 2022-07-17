package com.girogevoro.notesimple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.girogevoro.notesimple.repository.Note;
import com.girogevoro.notesimple.repository.NoteRepositoryImpl;

import java.net.BindException;
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
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_container, NoteListFragment.newInstance(this))
                    .commit();
        }
    }

    @Override
    public void setNote(Note note) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            int id = supportFragmentManager.getBackStackEntryAt(0).getId();
            supportFragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.list_info, NoteInfoFragment.newInstance(note))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.options_menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
}