package com.quantum.apps.simplynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecyclerView;
    private RecyclerView.Adapter notesAdapter;
    private RecyclerView.LayoutManager notesLayoutManager;
    private FloatingActionButton notesFab;
    private List<Note> notesData;

    private Repository<Note> notesRepository;
    private static final int CREATE_NEW_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRepository = new NoteRepository(this);

        notesRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        notesRecyclerView.setHasFixedSize(true);

        notesLayoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(notesLayoutManager);

        notesData = getNotes();

        notesAdapter = new NotesAdapter(notesData);
        notesRecyclerView.setAdapter(notesAdapter);

        notesFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        notesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent showEditNoteIntent = new Intent(context, CreateNoteActivity.class);
                startActivityForResult(showEditNoteIntent, CREATE_NEW_NOTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_NEW_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                long id = extras.getLong("resultId");
                String title = extras.getString("title");
                String date = extras.getString("date");

                Note note = new Note();

                note.setId(id);
                note.setEditDate(date);
                note.setTitle(title);

                notesData.add(0, note);
                notesAdapter.notifyItemInserted(0);
                notesLayoutManager.scrollToPosition(0);
            }
        }
    }

    private List<Note> getNotes() {
        return notesRepository.list();
    }

    @Override
    protected void onDestroy() {
        notesRepository.destroy();
        super.onDestroy();
    }
}
