package com.example.lostandfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LostFound.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_POST_TYPE = "post_type";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POST_TYPE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST_TYPE, item.getPostType());
        cv.put(COLUMN_NAME, item.getName());
        cv.put(COLUMN_PHONE, item.getPhone());
        cv.put(COLUMN_DESCRIPTION, item.getDescription());
        cv.put(COLUMN_DATE, item.getDate());
        cv.put(COLUMN_LOCATION, item.getLocation());
        cv.put(COLUMN_LATITUDE, item.getLatitude());
        cv.put(COLUMN_LONGITUDE, item.getLongitude());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result;
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_POST_TYPE, COLUMN_NAME, COLUMN_PHONE,
                        COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_LOCATION,
                        COLUMN_LATITUDE, COLUMN_LONGITUDE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Item item = new Item();
        if (cursor != null && cursor.moveToFirst()) {
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            item.setPostType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_TYPE)));
            item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            item.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
            item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            item.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
            item.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
            item.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)));
            item.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)));
            cursor.close();
        }
        return item;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                item.setPostType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_TYPE)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                item.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                item.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                item.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)));
                item.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)));
                item.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
