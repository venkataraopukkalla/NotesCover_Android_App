package com.vikas.notescover.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotesEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesRepro notesRepro();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "notes_cover")
                    .build();
        }
        return instance;
    }

}
