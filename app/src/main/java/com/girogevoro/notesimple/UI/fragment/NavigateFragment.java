package com.girogevoro.notesimple.UI.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.girogevoro.notesimple.R;
import com.girogevoro.notesimple.domian.repository.Note;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigateFragment extends Fragment {


    private static final String NODE = "node";
    private Note mNote;

    public NavigateFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NavigateFragment newInstance(Note note) {
        NavigateFragment fragment = new NavigateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NODE,note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mNote = (Note) getArguments().getParcelable(NODE);
        return inflater.inflate(R.layout.fragment_navigate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.remove_note).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Remove note", Toast.LENGTH_SHORT).show();
        });
        view.findViewById(R.id.edit_note).setOnClickListener(v -> {
            ((INoteListFragment) requireActivity()).editNote(mNote);
            Toast.makeText(requireContext(), "edit note", Toast.LENGTH_SHORT).show();
        });
    }
}