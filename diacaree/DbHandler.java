package com.example.diacaree;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DiaCareDatabase";

    private static final String TABLE_RECORD = "RecordTable";
    private static final String KEY_ID = "id";
    private static final String KEY_REC = "record";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    private static final String TABLE_USERS = "UserTable";
    private static final String KEY_USERID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";




    public DbHandler(@Nullable Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_RECORD + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_REC + " TEXT," + KEY_DATE + " TEXT," + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_USERTABLE =  "CREATE TABLE " + TABLE_USERS + "(" + KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT," + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }


    public void insetDetails(String record, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_REC, record);
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_TIME, time);

        Long newRowID = db.insert(TABLE_RECORD, null, contentValues);
        db.close();
    }

    public void insertNewUser(String email, String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);

        Long newRowID = db.insert(TABLE_USERS, null, contentValues);
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<HashMap<String, String>> recordList = new ArrayList<>();

        String query = "SELECT id, date, record, time FROM " + TABLE_RECORD;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            HashMap<String, String> record = new HashMap<>();
            record.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            record.put("date", cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            record.put("record", cursor.getString(cursor.getColumnIndex(KEY_REC)));
            record.put("time", cursor.getString(cursor.getColumnIndex(KEY_TIME)));
            recordList.add(record);
        }

        return recordList;
    }

    @SuppressLint("Range")
    public HashMap<String, String> getSingleRecord(int recordID){
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> record = new HashMap<>();
        String query = "SELECT id, record, date, time FROM " + TABLE_RECORD + " WHERE " + KEY_ID + " = " + recordID;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null && cursor.moveToFirst()){
            record.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            record.put("date", cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            record.put("record", cursor.getString(cursor.getColumnIndex(KEY_REC)));
            record.put("time", cursor.getString(cursor.getColumnIndex(KEY_TIME)));
            cursor.close();
        }
        return record;
    }

    @SuppressLint("Range")
    public String getGraphDataRecord(int recordID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  record FROM " + TABLE_RECORD + " WHERE " + KEY_ID + " = " + recordID;
        Cursor cursor = db.rawQuery(query, null);
        String dataRecord;
        if(cursor != null && cursor.moveToFirst()){
            dataRecord = cursor.getString(cursor.getColumnIndex(KEY_REC));
            cursor.close();
        }
        else{
            dataRecord = null;
        }

        return dataRecord;
    }

    @SuppressLint("Range")
    public String getGraphDataTime(int recordID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  time FROM " + TABLE_RECORD + " WHERE " + KEY_ID + " = " + recordID;
        Cursor cursor = db.rawQuery(query, null);
        String dataTime;
        if(cursor != null && cursor.moveToFirst()){
            dataTime = cursor.getString(cursor.getColumnIndex(KEY_TIME));
            cursor.close();
        }
        else{
            dataTime = null;
        }

        return dataTime;
    }

    public int updateRecordDetails(int id, String record, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_REC, record);
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_TIME, time);

        int count = db.update(TABLE_RECORD, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        return count;
    }

    public void deleteRecord(int recordID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORD, KEY_ID + " = ?", new String[]{String.valueOf(recordID)});
        db.close();
    }

    @SuppressLint("Range")
    public String getPassword(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT password FROM " + TABLE_USERS + " WHERE " + KEY_USERID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);

        String pass;
        if(cursor != null && cursor.moveToFirst()){
            pass = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            cursor.close();
        }
        else{
            pass = null;
        }

        return pass;
    }

    @SuppressLint("Range")
    public String getUserID(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  id FROM " + TABLE_USERS + " WHERE " + KEY_USERNAME + " = '" + userName + "'";
        Cursor cursor = db.rawQuery(query, null);
        String id;
        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getString(cursor.getColumnIndex(KEY_USERID));
            cursor.close();
        }
        else{
            id = null;
        }


        return id;
    }

    public boolean hasRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_RECORD;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            return true;

        }
        else {
            return false;
        }
    }
}
