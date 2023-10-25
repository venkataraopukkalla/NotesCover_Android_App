package com.vikas.notescover.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesRepro {
    //insert
    @Insert
    public void insert(NotesEntity notes);

    //retrive
    @Query("SELECT * FROM notes_details")
    List<NotesEntity>getAllNotes();

    // Delete all records
    @Query("DELETE FROM notes_details")
    void deleteAllNotes();

    //update
    @Update
    void update(NotesEntity notes);
    //delete

    @Delete
    void delete(NotesEntity notes);
}
