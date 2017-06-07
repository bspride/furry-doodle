package com.quantum.apps.simplynotes;

import android.provider.BaseColumns;

/**
 * Created by mgclarke on 6/5/17.
 */

public final class DatabaseContract {

    private DatabaseContract() {}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_CREATE_DATE = "createdate";
        public static final String COLUMN_NAME_EDIT_DATE = "editdate";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_TITLE + " TEXT," +
                COLUMN_NAME_CREATE_DATE + " TEXT," +
                COLUMN_NAME_EDIT_DATE + " TEXT," +
                COLUMN_NAME_DATA + " BLOB )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
