package com.quantum.apps.simplynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mgclarke on 6/7/17.
 */

public class NoteRepository implements Repository<Note> {
    private Context nContext;
    private NotesOpenHelper notesOpenHelper;

    public NoteRepository(Context context) {
        nContext = context;
        notesOpenHelper = new NotesOpenHelper(nContext);
    }

    @Override
    public Note item(long id) {
        SQLiteDatabase db = notesOpenHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.NoteEntry._ID,
                DatabaseContract.NoteEntry.COLUMN_NAME_TITLE,
                DatabaseContract.NoteEntry.COLUMN_NAME_CREATE_DATE,
                DatabaseContract.NoteEntry.COLUMN_NAME_EDIT_DATE,
                DatabaseContract.NoteEntry.COLUMN_NAME_DATA
        };

        String selection = DatabaseContract.NoteEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id)};

        Cursor cursor = db.query(
                DatabaseContract.NoteEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null );

        if (cursor != null)
            cursor.moveToFirst();

        long noteId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry.COLUMN_NAME_TITLE));
        String editDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry.COLUMN_NAME_EDIT_DATE));
        String createDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry.COLUMN_NAME_CREATE_DATE));
        byte[] blob = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry.COLUMN_NAME_DATA));
        String data = "";
        try {
            data = new String(blob, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        Note note = new Note();
        note.setId(noteId);
        note.setTitle(title);
        note.setEditDate(editDate);
        note.setCreateDate(createDate);
        note.setData(data);

        cursor.close();
        db.close();

        return note;
    }

    @Override
    public Note add(Note note) {
        SQLiteDatabase db = notesOpenHelper.getWritableDatabase();

        String title = note.getTitle();
        String data = note.getData();
        String date = new Date().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NoteEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(DatabaseContract.NoteEntry.COLUMN_NAME_DATA, data.getBytes());
        contentValues.put(DatabaseContract.NoteEntry.COLUMN_NAME_CREATE_DATE, date);
        contentValues.put(DatabaseContract.NoteEntry.COLUMN_NAME_EDIT_DATE, date);

        long newRowId = db.insert(DatabaseContract.NoteEntry.TABLE_NAME, null, contentValues);

        db.close();

        Note addedNote = new Note();

        addedNote.setId(newRowId);
        addedNote.setCreateDate(date);
        addedNote.setEditDate(date);
        addedNote.setTitle(title);
        addedNote.setData(data);

        return addedNote;
    }

    @Override
    public List<Note> list() {
        SQLiteDatabase db = notesOpenHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.NoteEntry._ID,
                DatabaseContract.NoteEntry.COLUMN_NAME_TITLE,
                DatabaseContract.NoteEntry.COLUMN_NAME_EDIT_DATE
        };

        String sortOrder =
                DatabaseContract.NoteEntry.COLUMN_NAME_EDIT_DATE + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.NoteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<Note> noteList = new ArrayList<Note>();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry.COLUMN_NAME_TITLE));
            String editDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NoteEntry.COLUMN_NAME_EDIT_DATE));

            Note note = new Note();

            note.setId(id);
            note.setEditDate(editDate);
            note.setTitle(title);

            noteList.add(note);
        }
        cursor.close();
        db.close();

        return noteList;
    }

    @Override
    public void destroy() {
        notesOpenHelper.close();
    }
}
