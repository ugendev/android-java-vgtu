package com.example.todos;

public class TodoFilter {
    public String title;
    public String category;
    public String date;
    public Integer completion;

    public TodoFilter(String title, String category, String date, Integer completion) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.completion = completion;
    }
}
