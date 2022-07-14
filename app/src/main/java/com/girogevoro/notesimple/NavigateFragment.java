package com.girogevoro.notesimple;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigateFragment extends Fragment {


    public NavigateFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NavigateFragment newInstance() {
        NavigateFragment fragment = new NavigateFragment();
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
        return inflater.inflate(R.layout.fragment_navigate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.remove_note).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Remove note", Toast.LENGTH_SHORT).show();
        });
        view.findViewById(R.id.edit_note).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "edit note", Toast.LENGTH_SHORT).show();
        });
    }
}