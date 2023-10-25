package com.vikas.notescover.model;

import android.database.DatabaseUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes_details")
public class NotesEntity  implements Serializable {

    @PrimaryKey (autoGenerate = true)
    private int id ;
    private String title;
    private  String story;

    public NotesEntity(String title, String story) {
        this.title = title;
        this.story = story;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    @Override
    public String toString() {
        return "NotesEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", story='" + story + '\'' +
                '}';
    }
}
