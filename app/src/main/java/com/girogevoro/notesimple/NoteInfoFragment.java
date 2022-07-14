package com.girogevoro.notesimple;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.girogevoro.notesimple.repository.Note;
import com.girogevoro.notesimple.repository.NoteRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NoteInfoFragment extends Fragment {

    private static final String NOTE = "index";
    private Note mNote;
    private TextView mDate;
    private AlertDialog mAlertDialog;

    public NoteInfoFragment() {
    }

    public static NoteInfoFragment newInstance(Note note) {
        NoteInfoFragment fragment = new NoteInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = (Note) getArguments().getParcelable(NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNote(view);

        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.beginTransaction()
                .replace(R.id.navigation, NavigateFragment.newInstance())
                .commit();

    }


    private void initNote(@NonNull View view) {
        view.setBackgroundColor(mNote.getColor());
        ((TextView) view.findViewById(R.id.title)).setText(mNote.getName());
        mDate = view.findViewById(R.id.time);
        setData();

        mDate.setOnClickListener(v -> {
            mAlertDialog = new AlertDialog.Builder(requireActivity()).create();

            View DialogView = requireActivity().getLayoutInflater().
                    inflate(R.layout.dialog_date_picker, null);
            DialogView.findViewById(R.id.set_date).setOnClickListener(v1 -> {
                DatePicker datePicker = (DatePicker) DialogView.findViewById(R.id.data_picker);
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                mNote.setDate(calendar.getTime());
                setData();
                mAlertDialog.dismiss();
            });
            mAlertDialog.setView(DialogView);
            mAlertDialog.show();
        });
        ((TextView) view.findViewById(R.id.description)).setText(mNote.getDescription());
    }

    private void setData() {
        String dd_mm_yyyy = new SimpleDateFormat(getString(R.string.format_date),
                Locale.getDefault()).format(mNote.getDate());
        mDate.setText(dd_mm_yyyy);
    }
}