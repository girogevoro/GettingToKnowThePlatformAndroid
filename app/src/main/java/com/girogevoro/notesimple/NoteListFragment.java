package com.girogevoro.notesimple;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.PopupMenu;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.girogevoro.notesimple.repository.Note;
import com.girogevoro.notesimple.repository.NoteRepositoryImpl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteListFragment extends Fragment {


    public NoteListFragment() {
        // Required empty public constructor
    }

    public static NoteListFragment newInstance(Activity activity) {
        NoteListFragment fragment;
        if (activity instanceof INoteListFragment) {
            fragment = new NoteListFragment();
        } else {
            throw new ExceptionInInitializerError("Need INoteListFragment activity");
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view.findViewById(R.id.list_notes));

        view.findViewById(R.id.add_note).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "add new note", Toast.LENGTH_SHORT).show();
        });
    }

    private void initList(LinearLayout mainLayout) {
        int cnt = 0;
        for (Note i : NoteRepositoryImpl.getInstance().getAll()) {
            TextView textView = new TextView(requireContext());
            textView.setText(i.getName());
            textView.setBackgroundColor(i.getColor());
            textView.setPadding(15, 15, 15, 15);
            mainLayout.addView(textView);

            textView.setOnClickListener(view -> {
                ((INoteListFragment) requireActivity()).setNote(i);
            });
            textView.setOnLongClickListener(view -> {
                initPopupMenu(mainLayout, textView, i);
                return true;
            });


        }

    }



    void initPopupMenu(LinearLayout mainLayout, View view, Note note) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), view);
        requireActivity().getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.remove_note) {
                mainLayout.removeView(view);
                NoteRepositoryImpl.getInstance().getAll().remove(note);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.options_menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            Toast.makeText(requireContext(), "todo send", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.sort_by_time) {
            Toast.makeText(requireContext(), "todo add sort by time", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.sort_by_name) {
            Toast.makeText(requireContext(), "todo sort by name", Toast.LENGTH_SHORT).show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}