package com.quantum.apps.simplynotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import us.feras.mdv.MarkdownView;

public class NoteActivity extends AppCompatActivity {
    private static final String NOTE_KEY = "NOTE";
    private MarkdownView markdownView;
    private Repository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteRepository = new NoteRepository(this);

        markdownView = (MarkdownView) findViewById(R.id.markdownView);

        Intent intent = getIntent();
        long id = intent.getLongExtra(NOTE_KEY, -1);

        if (id != -1)
            getNoteData(id);
    }

    @Override
    protected void onDestroy() {
        noteRepository.destroy();
        super.onDestroy();
    }

    private void getNoteData(long id) {
        Note note = (Note) noteRepository.item(id);

        markdownView.loadMarkdown(note.getData());
    }
}
