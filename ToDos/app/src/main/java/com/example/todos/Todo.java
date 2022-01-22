package com.example.todos;

import androidx.annotation.NonNull;

public class Todo {
    private int id;
    private String title;
    private String date;
    private String category;
    private int completed;

    public Todo(int id, String title, String date, String category, int completed) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
        this.completed = completed;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @NonNull
    @Override
    public String toString() {
        String status = getCompleted() == 0
            ? "Не завершено"
            : "Завершено";

        return String.format("%s \n%s | %s \n%s", category, title, status ,date);
    }
}
