package com.example.todos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class TodoEditActivity extends AppCompatActivity {
    private EditText todoTitle;
    private EditText todoDate;
    private Spinner todoCategory;
    private CheckBox todoCompletion;
    private Button deleteButton;

    private int todoId = 0;
    private final String[] TODO_CATEGORIES = new String[] { "Спорт", "Покупки", "Работа", "Досуг" };

    DbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit);

        ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.hide();
        }

        todoTitle = findViewById(R.id.todo_title);
        todoDate = findViewById(R.id.todo_date);
        todoCategory = findViewById(R.id.todo_category);
        todoCompletion = findViewById(R.id.todo_completed);
        deleteButton = findViewById(R.id.delete_btn);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TODO_CATEGORIES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoCategory.setAdapter(spinnerAdapter);

        db = new DbAdapter(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            todoId = extras.getInt("id");
        }

        if (todoId != 0) {
            db.open();

            Todo todo = db.getTodoById(todoId);

            todoTitle.setText(todo.getTitle());
            todoDate.setText(todo.getDate());
            todoCategory.setSelection(spinnerAdapter.getPosition(todo.getCategory()));
            todoCompletion.setChecked(todo.getCompleted() != 0);

            db.close();
        } else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    public void save(View view) {
        String title = todoTitle.getText().toString();
        String category = TODO_CATEGORIES[todoCategory.getSelectedItemPosition()];
        String date = todoDate.getText().toString();
        int completion = todoCompletion.isChecked() ? 1 : 0;

        Todo todo = new Todo(todoId, title, date, category, completion);

        db.open();

        if (todo.getId() != 0) {
            db.update(todo);
        } else {
            db.insert(todo);
        }

        db.close();
        showTodoList();
    }

    public void delete(View view) {
        db.open();
        db.delete(todoId);
        db.close();

        showTodoList();
    }

    private void showTodoList() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}