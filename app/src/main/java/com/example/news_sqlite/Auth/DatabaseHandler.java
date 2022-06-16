package com.example.news_sqlite.Auth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "usersManager",
            TABLE_USERS = "Users",
            KEY_UID = "uid",
            KEY_USERNAME = "username",
            KEY_PASSWORD = "password";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" + KEY_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }

    public void createUser(UsersDatabase user)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public UsersDatabase getUser(int id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_UID, KEY_USERNAME, KEY_PASSWORD}, KEY_UID + "=?", new String[] { String.valueOf(id)},null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UsersDatabase user = new UsersDatabase(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2));
        db.close();
        cursor.close();
        return user;
    }

    public void deleteUser(UsersDatabase user)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, KEY_UID + "=?", new String[] { String.valueOf(user.getUserId())});
        db.close();
    }

    public int getUserCount()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int updateUser(UsersDatabase user)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());

        int rowsAffected = db.update(TABLE_USERS, values, KEY_UID + "=?", new String[]{String.valueOf(user.getUserId())});
        db.close();

        return rowsAffected;
    }

    public List<UsersDatabase> getAllUsers()
    {
        List<UsersDatabase> users = new ArrayList<UsersDatabase>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(new     UsersDatabase(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }
}