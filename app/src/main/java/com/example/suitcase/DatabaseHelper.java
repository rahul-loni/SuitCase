package com.example.suitcase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String db_name="Users.db";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Users.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("Create Table Users(email TEXT primary key ,password TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("drop table if exists Users");
    }
    public Boolean insertUsers(String email,String password){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        Long Result=sqLiteDatabase.insert("Users",null,contentValues);
        if (Result==-1){
                 return  false;
        }else {
            return true;
        }
    }
    public Boolean updatePassword(String email,String password){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        int Result=sqLiteDatabase.update("Users",null,contentValues);
        if (Result==-1){
            return  false;
        }else {
            return true;
        }

    }
    public Boolean checkEmail(String email){
     SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase
                .rawQuery("Select * from users where email=?",new String[]{email});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email , String password){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase
                .rawQuery("Select * from users where email=? and password=?",new String[]{email,password});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}
