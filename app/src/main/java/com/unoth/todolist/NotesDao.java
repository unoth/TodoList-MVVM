package com.unoth.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Insert
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);
}
