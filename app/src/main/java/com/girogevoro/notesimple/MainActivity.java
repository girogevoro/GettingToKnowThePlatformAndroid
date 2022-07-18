package com.girogevoro.notesimple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.girogevoro.notesimple.repository.Note;
import com.girogevoro.notesimple.repository.NoteRepositoryImpl;
import com.google.android.material.navigation.NavigationView;

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
        setSupportActionBar(findViewById(R.id.toolbar));
        initDrawer(findViewById(R.id.drawer));
    }

    @Override
    public void setNote(Note note) {
        oneFrame()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.list_info, NoteInfoFragment.newInstance(note))
                .commit();
    }

    private FragmentManager oneFrame() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            int id = supportFragmentManager.getBackStackEntryAt(0).getId();
            supportFragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return supportFragmentManager;
    }

    void initDrawer(View v) {
        //Toolbar view = new Toolbar(requireContext());
        //fragmentActivity.setSupportActionBar(view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                ((DrawerLayout) v),
                v.findViewById(R.id.toolbar),
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        ((DrawerLayout) v).addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navView = findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.about_app) {
                    oneFrame()
                            .beginTransaction()
                            .addToBackStack("")
                            .replace(R.id.list_info, AboutTheAppFragment.newInstance())
                            .commit();
                    ((DrawerLayout) v).close();
                    return true;
                }
                return false;
            }
        });
    }

}