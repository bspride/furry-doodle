package com.quantum.apps.simplynotes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by mgclarke on 6/4/17.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private List<Note> notesData;

    public static class NoteHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView titleTextView;
        private TextView editDateTextView;
        private long noteId;
        private static final String NOTE_KEY = "NOTE";

        public NoteHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.note_title);
            editDateTextView = (TextView) v.findViewById(R.id.note_edit_date);
            v.setOnClickListener(this);
        }

        public void bindNote(Note note) {
            noteId = note.getId();
            titleTextView.setText(note.getTitle());
            editDateTextView.setText(note.getEditDate());
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showNoteIntent = new Intent(context, NoteActivity.class);
            showNoteIntent.putExtra(NOTE_KEY, noteId);
            context.startActivity(showNoteIntent);
        }
    }

    public NotesAdapter(List<Note> notes) {
        notesData = notes;
    }

    @Override
    public NotesAdapter.NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_text_view, parent, false);

        NoteHolder nh = new NoteHolder(v);
        return nh;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.NoteHolder holder, int position) {
        holder.bindNote(notesData.get(position));
    }

    @Override
    public int getItemCount() {
        return notesData.size();
    }
}
