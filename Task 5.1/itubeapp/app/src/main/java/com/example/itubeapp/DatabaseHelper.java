package com.example.itubeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iTubeApp.db";
    private static final int DATABASE_VERSION = 1;

    //user table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    //playlist table
    public static final String TABLE_PLAYLIST = "playlist";
    public static final String COLUMN_PLAYLIST_ID = "playlist_id";
    public static final String COLUMN_VIDEO_URL = "video_url";

    //create users table SQL
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FULL_NAME + " TEXT NOT NULL, "
            + COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, "
            + COLUMN_PASSWORD + " TEXT NOT NULL" + ")";

    //create playlist table SQL
    private static final String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLIST + "("
            + COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_VIDEO_URL + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        onCreate(db);
    }
}