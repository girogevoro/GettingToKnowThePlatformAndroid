package com.girogevoro.notesimple.UI.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.girogevoro.notesimple.R;
import com.girogevoro.notesimple.domian.repository.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNoteFragment extends Fragment {

    private static final String NOTE = "index";
    private Note mNote;
    private TextView mDate;
    private AlertDialog mAlertDialog;

    public EditNoteFragment() {
    }

    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNote(view);

    }


    private void initNote(@NonNull View view) {
        view.setBackgroundColor(mNote.getColor());
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mNote.getName());
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mNote.setName(editable.toString());
            }
        });


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
        EditText description = (EditText) view.findViewById(R.id.description);
        description.setText(mNote.getDescription());

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mNote.setDescription(editable.toString());
            }
        });


    }

    private void setData() {
        String dd_mm_yyyy = new SimpleDateFormat(getString(R.string.format_date),
                Locale.getDefault()).format(mNote.getDate());
        mDate.setText(dd_mm_yyyy);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteListFragment.KEY_NODE, mNote);
        getParentFragmentManager().setFragmentResult(NoteListFragment.REPLACE, bundle);
    }
}