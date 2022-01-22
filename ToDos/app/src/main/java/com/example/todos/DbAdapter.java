package com.example.todos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter{
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbAdapter(Context context) {
        dbHelper = new DbHelper(context.getApplicationContext());
    }

    public DbAdapter open() {
        db = dbHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getEntries() {
        String[] columns = new String[] {dbHelper.COL_ID, dbHelper.COL_TITLE, dbHelper.COL_CATEGORY, dbHelper.COL_DATE, dbHelper.COL_COMPLETED};

        return db.query(dbHelper.TABLE, columns, null, null, null ,null ,null);
    }

    public List<Todo> getTodos() {
        ArrayList<Todo> todos = new ArrayList<>();
        Cursor cursor = getEntries();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ID));
            @SuppressLint("Range") int completed = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_COMPLETED));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(dbHelper.COL_TITLE));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(dbHelper.COL_DATE));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(dbHelper.COL_CATEGORY));

            todos.add(new Todo(id, title, date, category, completed));
        }

        cursor.close();

        return todos;
    }

    public Todo getTodoById(int id) {
        Todo todo = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DbHelper.TABLE, DbHelper.COL_ID);
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(id) });

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int completed = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_COMPLETED));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(dbHelper.COL_TITLE));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(dbHelper.COL_DATE));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(dbHelper.COL_CATEGORY));

            todo = new Todo(id, title, date, category, completed);
        }

        cursor.close();

        return todo;
    }

    public int insert(Todo todo) {
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.COL_TITLE, todo.getTitle());
        cv.put(dbHelper.COL_DATE, todo.getDate());
        cv.put(dbHelper.COL_CATEGORY, todo.getCategory());
        cv.put(dbHelper.COL_COMPLETED, todo.getCompleted());

        return (int) db.insert(dbHelper.TABLE, null, cv);
    }

    public int update(Todo todo) {
        String whereClause = dbHelper.COL_ID + "=" + todo.getId();
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.COL_TITLE, todo.getTitle());
        cv.put(dbHelper.COL_DATE, todo.getDate());
        cv.put(dbHelper.COL_CATEGORY, todo.getCategory());
        cv.put(dbHelper.COL_COMPLETED, todo.getCompleted());

        return db.update(dbHelper.TABLE, cv, whereClause, null);
    }

    public int delete(int todoId) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[] { String.valueOf(todoId) };

        return db.delete(dbHelper.TABLE, whereClause, whereArgs);
    }

    public List<Todo> getByFilter(TodoFilter filter) {
        String conditions;

        conditions = String.format("SELECT * FROM %s WHERE 1=1", dbHelper.TABLE);

        if (filter.title != null && filter.title.length() != 0) {
            conditions += String.format(" and %s like '%s'", dbHelper.COL_TITLE, "%" + filter.title + "%");
        }

        if (filter.date != null && filter.date.length() != 0) {
            conditions += String.format(" and %s = '%s'", dbHelper.COL_DATE, filter.date);
        }

        if (filter.completion != null) {
            conditions += String.format(" and %s = %s", dbHelper.COL_COMPLETED, filter.completion);
        }

        if (filter.category != null && filter.category.length() != 0) {
            conditions += String.format(" and %s = '%s'", dbHelper.COL_CATEGORY, filter.category);
        }

        Cursor cursor = db.rawQuery(conditions, null);
        ArrayList<Todo> todos = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ID));
            @SuppressLint("Range") int completed = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_COMPLETED));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(dbHelper.COL_TITLE));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(dbHelper.COL_DATE));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(dbHelper.COL_CATEGORY));

            todos.add(new Todo(id, title, date, category, completed));
        }

        cursor.close();

        return todos;
    }
}
