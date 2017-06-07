package com.quantum.apps.simplynotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mgclarke on 6/5/17.
 */

public class NotesOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "simplynotes.db";

    public NotesOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // TODO: What to do with exception
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DatabaseContract.NoteEntry.CREATE_TABLE);
        } catch (Exception e) {
            Log.e("Error", "on create", e);
        }
    }

    // TODO: Implement better code
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.NoteEntry.DELETE_TABLE);
        onCreate(db);
    }
}
