package com.example.todos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todos.db";
    private static final int SCHEMA = 2;

    public static final String TABLE = "todo";

    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_DATE = "date";
    public static final String COL_CATEGORY = "category";
    public static final String COL_COMPLETED = "completed";

    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_INT = "INTEGER";
    public static final String TYPE_PK = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            String.format("CREATE TABLE %s (", TABLE) +
            String.format("%s %s,", COL_ID, TYPE_PK) +
            String.format("%s %s,", COL_TITLE, TYPE_TEXT) +
            String.format("%s %s,", COL_DATE, TYPE_TEXT) +
            String.format("%s %s,", COL_CATEGORY, TYPE_TEXT) +
            String.format("%s %s);", COL_COMPLETED, TYPE_INT)
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
