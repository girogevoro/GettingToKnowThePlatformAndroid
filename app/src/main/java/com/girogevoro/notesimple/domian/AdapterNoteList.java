package com.girogevoro.notesimple.domian;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.girogevoro.notesimple.R;
import com.girogevoro.notesimple.domian.repository.Note;
import com.girogevoro.notesimple.domian.repository.NoteRepositoryImpl;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterNoteList extends RecyclerView.Adapter<AdapterNoteList.ViewHolder> {
    public interface OnClickCard {
        void onClick(Note note);
    }

    ;
    List<Note> mList;
    SimpleDateFormat mFormat;
    OnClickCard mOnClickCard;

    public AdapterNoteList(List<Note> list) {
        mFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        mList = list;
    }


    public void setOnClickCard(OnClickCard onClickCard) {
        mOnClickCard = onClickCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mList.get(position);
        holder.set(note.getName(), mFormat.format(note.getDate()), note.getColor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final View mCard;
        private final MaterialTextView mDate;
        private final MaterialTextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mDate = itemView.findViewById(R.id.date);
            mCard = itemView;

            mCard.setOnClickListener(view -> {
                if (mOnClickCard != null) {
                    mOnClickCard.onClick(mList.get(getAdapterPosition()));
                }
            });
            mCard.setOnLongClickListener(view -> {
                initPopupMenu(view, mList.get(getAdapterPosition()));
                return true;
            });

        }

        public void set(String title, String date, Integer color) {
            mTitle.setText(title);
            mDate.setText(date);
            mCard.setBackgroundColor(color);
        }

        void initPopupMenu(View view, Note note) {
            PopupMenu popupMenu = new PopupMenu(mCard.getContext(), view);
            ((Activity) mCard.getContext()).getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.remove_note) {
                    NoteRepositoryImpl.getInstance().getAll().remove(note);
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        }

    }
}
