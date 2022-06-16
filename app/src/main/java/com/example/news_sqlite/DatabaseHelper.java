package com.example.news_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHelper extends SQLiteOpenHelper {

    String TableName = "table_name";

    public DatabaseHelper(Context context){
        super(context,"database_name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sQuery = "create table " + TableName
                +"(id INTEGER primary key autoincrement, text TEXT)";
        sqLiteDatabase.execSQL(sQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sQuery = "drop table if exists " + TableName;
        sqLiteDatabase.execSQL(sQuery);
        onCreate(sqLiteDatabase);
    }

    public void insert(String text){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", text);
        database.insertWithOnConflict(TableName, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
    }

    public void update(String id, String text){
        SQLiteDatabase database = getWritableDatabase();
        String sQuery = "update " + TableName + " set text='" + text
                +"'where id='" + id + "'";
        database.execSQL(sQuery);
        database.close();
    }

    public void delete(String id){
        SQLiteDatabase database = getWritableDatabase();
        String sQuery = "delete from " + TableName + " where id='" + id +"'";
        database.execSQL(sQuery);
        database.close();
    }

    public void truncate(){
        SQLiteDatabase database = getWritableDatabase();
        String sQuery1 = "delete from " + TableName;
        String sQuery2 = "delete from sqlite_sequence where name='"
                + TableName + "'";
        database.execSQL(sQuery1);
        database.execSQL(sQuery2);
        database.close();
    }

    public JSONArray getArray(){
        SQLiteDatabase database = getReadableDatabase();
        JSONArray jsonArray = new JSONArray();
        String sQuery = "select * from " + TableName;
        Cursor cursor = database.rawQuery(sQuery, null);
        if (cursor.moveToFirst()){
            do {
                JSONObject object = new JSONObject();
            try {
                object.put("id", cursor.getString(0));
                object.put("text", cursor.getString(1));
                jsonArray.put(object);
            }catch(JSONException e){
                e.printStackTrace();
            }
                } while (cursor.moveToNext());
        } cursor.close();
        database.close();
        return jsonArray;
    }
}
