package com.girogevoro.notesimple;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList((LinearLayout) view.findViewById(R.id.list_notes));

        view.findViewById(R.id.add_note).setOnClickListener(v->{
            Toast.makeText(requireContext(),"add new note", Toast.LENGTH_SHORT).show();
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
        }

    }
}