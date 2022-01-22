package com.example.todos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private ListView todosList;
    private ArrayAdapter<Todo> todos;

    private DbAdapter db;

    private EditText filterTitle;
    private Spinner filterCategory;
    private Spinner filterCompletion;
    private EditText filterDate;

    private final String[] TODO_CATEGORIES = new String[] { "", "Спорт", "Покупки", "Работа", "Досуг" };
    private final String[] TODO_STATUSES = new String[] { "", "Завершено", "Не завершено" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.hide();
        }

        filterTitle = findViewById(R.id.filter_title);
        filterCategory = findViewById(R.id.filter_category);
        filterCompletion = findViewById(R.id.filter_completion);
        filterDate = findViewById(R.id.filter_date);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TODO_CATEGORIES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCategory.setAdapter(spinnerAdapter);

        ArrayAdapter completedAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TODO_STATUSES);
        completedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCompletion.setAdapter(completedAdapter);

        todosList = findViewById(R.id.todosList);
        todosList.setOnItemClickListener((adapterView, view, position, l) -> {
          Todo todo = todos.getItem(position);

          if (todo != null) {
              Intent intent = new Intent(getApplicationContext(), TodoEditActivity.class);

              intent.putExtra("id", todo.getId());
              startActivity(intent);
          }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = new DbAdapter(this);
        db.open();

        todos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, db.getTodos());
        todosList.setAdapter(todos);

        db.close();
    }

    public void addTodo(View view) {
        Intent intent = new Intent(getApplicationContext(), TodoEditActivity.class);
        startActivity(intent);
    }

    public void filter(View view) {
        db.open();

        String date = filterDate.getText().toString();
        String title = filterTitle.getText().toString();
        String category = TODO_CATEGORIES[filterCategory.getSelectedItemPosition()];
        String completedText = TODO_STATUSES[filterCompletion.getSelectedItemPosition()];
        Integer completed;

        switch (completedText) {
            case "Завершено": completed = 1; break;
            case "Не завершено": completed = 0; break;
            default: completed = null;
        }

        TodoFilter filter = new TodoFilter(title, category, date, completed);

        todos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, db.getByFilter(filter));
        todosList.setAdapter(todos);

        db.close();
    }

    public void clear(View view) {
        filterTitle.setText("");
        filterCategory.setSelection(0);
        filterCompletion.setSelection(0);
        filterDate.setText("");

        filter(null);
    }
}