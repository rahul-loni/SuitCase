package com.example.suitcase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.renderscript.Sampler;

import androidx.annotation.Nullable;

public class Items_DBHelper extends SQLiteOpenHelper {
    //create database  name
    public static final String DBName="ITEMS_INFO_DB";
    //create database version
    public static final int DBVersion=1;
    //create database table name
    public static final String TABLE_NAME="ITEMS_INFO_TABLE";

    //create Db table Columns
    public static final String COLUMN_ID="id";
    public static final String NAME="name";
    public static final String PRICE="price";
    public static final String DESCRIPTION="description";
    public static final String IMAGE="image";
    public static final String PURCHASED="purchased";


    public Items_DBHelper(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String sqlQuery="CREATE TABLE " +" TABLE_NAME "+"("+
            COLUMN_ID+ "INTEGER PRIMARY KEY AUTOINCREMENT ,"+
            NAME + "TEXT NOT NULL," +
            PRICE + "TEXT NOT NULL," +
            DESCRIPTION + "TEXT," +
            IMAGE + "TEXT" +
            PURCHASED + "INTEGER)";
    db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       String sqlQuery="DROP TABLE IF EXISTS " + TABLE_NAME;
       db.execSQL(sqlQuery);
       onCreate(db);
    }
    public Cursor queryData(String sqlQuery){
        SQLiteDatabase database=getWritableDatabase();
        return  database.rawQuery(sqlQuery,null);
    }
    public Boolean insert(
            String name,
            String price,
            String description,
            String image,
            String purchased
    ){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO " +TABLE_NAME+ " VALUES (NULL,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
        sqLiteStatement.clearBindings();;
        sqLiteStatement.bindString(1,name);
        sqLiteStatement.bindString(2,price);
        sqLiteStatement.bindString(3,description);
        sqLiteStatement.bindString(4,image);
        sqLiteStatement.bindString(5, purchased   );
        long result=sqLiteStatement.executeInsert();
        database.close();
        return result!=-1;
    }
}
