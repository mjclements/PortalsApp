package com.example.mjclements.myapplication;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;
import java.util.UUID;

public class Scene {
    public UUID ID;
    public int time;
    public boolean liked;
    public Timestamp timestamp;

    // A dummy Scene constructor, useful only for testing //
    public Scene(int time, boolean liked) {
        this.ID = ID.randomUUID();
        this.time = time;
        this.liked = liked;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
//    public List<String> tag;
    //The real Scene constructor //
    public Scene(UUID ID, int time, boolean liked) {
        this.ID = ID;
        this.time = time;
        this.liked = liked;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void delete_scene() {
        //deletes the scene
    }

    public void update_scene(int time) {
        if (time > this.time) {
            this.time = time;
        }
    }
}


