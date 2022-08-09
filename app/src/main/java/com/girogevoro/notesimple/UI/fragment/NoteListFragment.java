package com.girogevoro.notesimple.UI.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.girogevoro.notesimple.R;
import com.girogevoro.notesimple.UI.dialogFragment.AddNoteDialogFragment;
import com.girogevoro.notesimple.domian.AdapterNoteList;
import com.girogevoro.notesimple.domian.repository.Note;
import com.girogevoro.notesimple.domian.repository.NoteRepositoryImpl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteListFragment extends Fragment {

    private static final int REMOVE_DURATION = 1000;
    private static final int CHANGE_DURATION = 1000;
    private static final int ADD_DURATION = 1000;
    private AdapterNoteList mAdapterNoteList;

    public static final String ADD = "NoteListFragment_add";
    public static final String REMOVE = "NoteListFragment_remove";
    public static final String REPLACE = "NoteListFragment_replace";

    public static final String KEY_NODE = "key_node";
    private RecyclerView mRecyclerView;


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
        mRecyclerView = view.findViewById(R.id.list_notes);
        initList(mRecyclerView);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(ADD_DURATION);
        defaultItemAnimator.setChangeDuration(CHANGE_DURATION);
        defaultItemAnimator.setRemoveDuration(REMOVE_DURATION);
        mRecyclerView.setItemAnimator(defaultItemAnimator);

        view.findViewById(R.id.add_note).setOnClickListener(v -> {
            AddNoteDialogFragment addNoteDialogFragment = AddNoteDialogFragment.newInstance(requireActivity());
            addNoteDialogFragment.show(getParentFragmentManager(), AddNoteDialogFragment.TAG);
        });

        setResultFragment();

    }

    private void setResultFragment() {
        getParentFragmentManager().setFragmentResultListener(ADD, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getParcelable(KEY_NODE);

                NoteRepositoryImpl instance = NoteRepositoryImpl.getInstance();
                instance.add(note);
                int position = instance.length() - 1;
                mAdapterNoteList.notifyItemInserted(position);
                mRecyclerView.smoothScrollToPosition(position);
            }
        });


        getParentFragmentManager().setFragmentResultListener(REMOVE, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getParcelable(KEY_NODE);

                NoteRepositoryImpl instance = NoteRepositoryImpl.getInstance();
                int position = instance.position(note);
                instance.remove(note);
                mAdapterNoteList.notifyItemRemoved(position);
            }
        });

        getParentFragmentManager().setFragmentResultListener(REPLACE, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getParcelable(KEY_NODE);

                NoteRepositoryImpl instance = NoteRepositoryImpl.getInstance();
                instance.add(note);
                int position = instance.position(note);
                mAdapterNoteList.notifyItemChanged(position);
                mRecyclerView.smoothScrollToPosition(position);
            }

        });
    }

    private void initList(RecyclerView recyclerView) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        mAdapterNoteList = new AdapterNoteList(
                this, NoteRepositoryImpl.getInstance().getAll());

        mAdapterNoteList.setOnClickCard(note -> ((INoteListFragment) requireActivity()).setNote(note));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapterNoteList);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

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