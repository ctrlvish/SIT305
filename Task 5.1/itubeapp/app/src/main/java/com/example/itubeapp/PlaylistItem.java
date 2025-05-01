package com.example.itubeapp;

public class PlaylistItem {
    private int id;
    private int userId;
    private String videoUrl;

    public PlaylistItem() {}

    public PlaylistItem(int userId, String videoUrl) {
        this.userId = userId;
        this.videoUrl = videoUrl;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}