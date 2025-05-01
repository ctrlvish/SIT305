package com.example.newsapp;

public class NewsItem {
    private String title;
    private String summary;
    private int imageResourceId;
    private String fullDescription;
    private int id;
    private boolean isTopStory;

    public NewsItem(int id, String title, String summary, int imageResourceId, String fullDescription, boolean isTopStory) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.imageResourceId = imageResourceId;
        this.fullDescription = fullDescription;
        this.isTopStory = isTopStory;
    }

    //getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public int getImageResourceId() { return imageResourceId; }
    public void setImageResourceId(int imageResourceId) { this.imageResourceId = imageResourceId; }
    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public boolean isTopStory() { return isTopStory; }
    public void setTopStory(boolean topStory) { isTopStory = topStory; }
}