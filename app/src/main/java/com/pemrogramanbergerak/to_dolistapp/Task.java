package com.pemrogramanbergerak.to_dolistapp;

public class Task {
    public Task(int id, String name, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private boolean isCompleted;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
