package com.ibo.musicplayerapp;

import java.io.Serializable;

public class Song implements Serializable {
    String title;
    String path;
    String artist;

    public Song(String title, String artist, String path) {
        this.title = title;
        this.path = path;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
