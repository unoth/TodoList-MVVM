package com.unoth.todolist;


import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
    private ArrayList<Note> notes;
    Random random = new Random();

    @Before
    public void setUp() throws Exception {
        notes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Note note = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(note);
        }
    }

    @Test
    public void whenAdded10ElementsThenSizeMustBe10() {
        assertEquals(10, notes.size());
    }

    @Test
    public void whenElementRemovedByIndexThenSizeMustBeDecreased() {
        notes.remove(1);
        assertEquals(9, notes.size());
    }

    @Test
    public void whenElementRemovedThenSizeMustBeDecreased() {
        Note note = new Note("Note test", 3);
        notes.add(note);
        assertEquals(11, notes.size());
        notes.remove(note);
        assertEquals(10, notes.size());
    }

    @Test
    public void whenNonExistentElementRemovedReturnFalse() {
        Note note = new Note("Note test", 3);
        assertFalse(notes.remove(note));
        assertEquals(10, notes.size());
    }

    @Test
    public void methodGetReturnRightValue() {
        Note note = notes.get(0);
        assertEquals("Note 0", note.getText());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenIndexOutOfBoundsThenTrowsException() {
        Note note = notes.get(10);
    }
}