package com.unoth.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.ArrayList;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes")
    ArrayList<Note> getNote();

    @Insert
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);
}
