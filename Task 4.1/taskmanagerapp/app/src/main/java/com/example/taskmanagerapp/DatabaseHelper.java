package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TasksDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DUE_DATE = "due_date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DUE_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(String title, String description, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DUE_DATE, dueDate);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<TaskModel> getAllTasks() {
        List<TaskModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " ORDER BY substr(" + COLUMN_DUE_DATE + ", 7, 4)," +  //year
                " substr(" + COLUMN_DUE_DATE + ", 4, 2)," +           //month
                " substr(" + COLUMN_DUE_DATE + ", 1, 2) ASC";         //date

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                task.setId(cursor.getInt(0));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public void updateTask(int id, String title, String description, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DUE_DATE, dueDate);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
