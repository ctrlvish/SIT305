package com.example.taskmanagerapp;

public class TaskModel {
    private int id;
    private String title;
    private String description;
    private String dueDate;

    public TaskModel(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}


