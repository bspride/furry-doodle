package com.quantum.apps.simplynotes;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by mgclarke on 6/7/17.
 */
@RunWith(AndroidJUnit4.class)
public class SQLiteTest {
    private static Repository<Note> notesRepository;
    private final String NOTE_TITLE = "Test note";

    @BeforeClass
    public static void setup() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(NotesOpenHelper.DATABASE_NAME);
        notesRepository = new NoteRepository(InstrumentationRegistry.getTargetContext());
    }

    @AfterClass
    public static void tearDown() {
        notesRepository.destroy();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(notesRepository);
    }

    @Test
    public void testAdd() {
        Note note = new Note();
        note.setTitle(NOTE_TITLE);
        Note newNote = notesRepository.add(note);
        assertEquals(1, newNote.getId());
    }

    @Test
    public void testList() {
        List<Note> noteList = notesRepository.list();
        assertEquals(1, noteList.size());
    }

    @Test
    public void testGet() {
        Note note = notesRepository.item(1);
        assertEquals(NOTE_TITLE, note.getTitle());
    }
}
