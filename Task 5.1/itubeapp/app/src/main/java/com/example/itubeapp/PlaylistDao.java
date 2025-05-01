package com.example.itubeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDao {
    private DatabaseHelper dbHelper;

    public PlaylistDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //add a video to playlist
    public long addToPlaylist(int userId, String videoUrl) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        values.put(DatabaseHelper.COLUMN_VIDEO_URL, videoUrl);

        long result = db.insert(DatabaseHelper.TABLE_PLAYLIST, null, values);
        db.close();
        return result;
    }

    //get all playlist items for a user
    public List<PlaylistItem> getUserPlaylist(int userId) {
        List<PlaylistItem> playlist = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_PLAYLIST_ID,
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_VIDEO_URL
        };
        String selection = DatabaseHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PLAYLIST,
                columns,
                selection,
                selectionArgs,
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                PlaylistItem item = new PlaylistItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PLAYLIST_ID)));
                item.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)));
                item.setVideoUrl(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_VIDEO_URL)));
                playlist.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return playlist;
    }
}