package com.example.itubeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //add a new user
    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FULL_NAME, user.getFullName());
        values.put(DatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(DatabaseHelper.COLUMN_PASSWORD, user.getPassword());

        long userId = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();
        return userId;
    }

    //get user by username
    public User getUserByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_FULL_NAME,
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_PASSWORD
        };
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)));
            user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FULL_NAME)));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD)));
            cursor.close();
        }
        db.close();
        return user;
    }

    //check if username exists
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USER_ID};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
}