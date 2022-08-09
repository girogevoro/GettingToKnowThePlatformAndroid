package com.girogevoro.notesimple.UI.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.girogevoro.notesimple.R;
import com.girogevoro.notesimple.UI.fragment.AboutTheAppFragment;
import com.girogevoro.notesimple.UI.fragment.EditNoteFragment;
import com.girogevoro.notesimple.UI.fragment.INoteListFragment;
import com.girogevoro.notesimple.UI.fragment.NoteInfoFragment;
import com.girogevoro.notesimple.UI.fragment.NoteListFragment;
import com.girogevoro.notesimple.domian.repository.Note;
import com.girogevoro.notesimple.domian.repository.NoteRepositoryImpl;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements INoteListFragment {

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

    @Override
    public void editNote(Note note) {
        oneFrame()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.list_info, EditNoteFragment.newInstance(note))
                .commit();
    }

    @Override
    public void addNote(Note note) {
         NoteRepositoryImpl.getInstance(this).add(note);
         recreate();
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


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Do you want to exit the program")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            Toast.makeText(MainActivity.this, "exit", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("no", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}