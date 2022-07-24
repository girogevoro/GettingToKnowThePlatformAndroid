package com.girogevoro.notesimple;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.girogevoro.notesimple.repository.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class AddNoteDialogFragment extends DialogFragment {

    public static final String TAG = "AddNoteDialogFragment";
    private static final String KEY_DATE = "KEY_DATE";
    private Note mNote;
    private TextView mDate;
    private AlertDialog mAlertDialog;

    public static AddNoteDialogFragment newInstance(Activity activity) {
        if (activity instanceof INoteListFragment) {

            return new AddNoteDialogFragment();
        } else {
            throw new ExceptionInInitializerError("need INoteListFragment activity");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        if (savedInstanceState == null) {

            mNote = new Note("",
                    Calendar.getInstance().getTime(),
                    " ",
                    new Random().nextInt(0xffffff) + 0xff000000);
        } else {
            mNote = new Note("",
                    ((Date) savedInstanceState.getSerializable(KEY_DATE)),
                    " ",
                    new Random().nextInt(0xffffff) + 0xff000000);
        }

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


        view.findViewById(R.id.button_ok).setOnClickListener(v -> {
            mNote.setName(((EditText) view.findViewById(R.id.add_note)).getText().toString());
            mNote.setDescription(((EditText) view.findViewById(R.id.description)).getText().toString());
            ((INoteListFragment) requireActivity()).addNote(mNote);
            dismiss();
        });


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(KEY_DATE, mNote.getDate());
        super.onSaveInstanceState(outState);
    }

    private void setData() {
        String dd_mm_yyyy = new SimpleDateFormat(getString(R.string.format_date),
                Locale.getDefault()).format(mNote.getDate());
        mDate.setText(dd_mm_yyyy);
    }
}
