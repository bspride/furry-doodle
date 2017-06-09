package com.quantum.apps.simplynotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.note_action_bar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.markdownView = (MarkdownView) findViewById(R.id.markdownView);


        Intent intent = getIntent();
        long id = intent.getLongExtra(NOTE_KEY, -1);

        if (id != -1)
            markdownView.loadMarkdown(getNoteData(id));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_note) {
            int x = 99;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        noteRepository.destroy();
        super.onDestroy();
    }

    private String getNoteData(long id) {
        Note note = (Note) noteRepository.item(id);
        String encodedData = note.getData().replaceAll("(\n)", "<br />");
        return encodedData;
    }
}
