package com.vikas.notescover.model;

public class Notes {

    private String title;
    private String story;
    private String date;


    //construter
    public Notes(String title, String story, String date) {
        this.title = title;
        this.story = story;
        this.date = date;
    }
    //getters
    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public String getDate() {
        return date;
    }



}
