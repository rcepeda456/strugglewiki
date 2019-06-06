package com.example.bryanmarchena.draftgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GameItem {

    private String title, description, publisher, date, consoles, categories;


    public GameItem(){

    }

    public void setTitle(String titl){
        title = titl;
    }

    public void setDescription(String des){
        description = des;
    }

    public void setPublisher(String pub){
        publisher = pub;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setConsoles(String consoles) {
        this.consoles = consoles;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getCategories() {
        return this.categories;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getConsoles() {
        return consoles;
    }

    public String getDescription() {
        return description;
    }

}
