package com.quantum.apps.simplynotes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {

    private Repository noteRepository;
    private EditText titleEditText;
    private MultiAutoCompleteTextView noteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.create_action_bar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        noteRepository = new NoteRepository(this);

        titleEditText = (EditText) findViewById(R.id.new_note_title);
        noteTextView = (MultiAutoCompleteTextView) findViewById(R.id.new_note_text);
    }

    public void saveNote(View v) {
        Note note = new Note();
        note.setTitle(titleEditText.getText().toString());
        note.setData(noteTextView.getText().toString());

        Note addedNote = (Note) noteRepository.add(note);

        Intent returnIntent = new Intent();
        Bundle extras = new Bundle();
        extras.putLong("resultId", addedNote.getId());
        extras.putString("title", addedNote.getTitle());
        extras.putString("date", addedNote.getCreateDate());
        returnIntent.putExtras(extras);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        noteRepository.destroy();
        super.onDestroy();
    }
}
