package com.example.suitcase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //create variable for database name ,database table and column
    public static final String DB_NAME = "SuitCase.db";
    public static final int DB_VERSION = 1;
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TABLE_NAME = "Users";
    public static final String ITEM_TABLE_NAME = "Items";
    // table columns
    public static final String ITEM_COLUMN_ID = "id";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_DESCRIPTION = "description";
    public static final String ITEM_IMAGE = "image";
    public static final String ITEM_PURCHASED = "purchased";

    //context for connection other class
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //Create Database SqlQuery
    @Override
    public void onCreate(SQLiteDatabase db) {
        //User Create Method
        String userTableSqlQuery = "CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_EMAIL + " TEXT PRIMARY KEY, " +
                USER_PASSWORD + " TEXT)";
//Items Create Method
        String itemTableSqlQuery = "CREATE TABLE " + ITEM_TABLE_NAME + " (" +
                ITEM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_NAME + " TEXT NOT NULL, " +
                ITEM_PRICE + " TEXT NOT NULL, " +
                ITEM_DESCRIPTION + " TEXT, " +
                ITEM_IMAGE + " TEXT, " +
                ITEM_PURCHASED + " INTEGER)";
        try {
            db.execSQL(itemTableSqlQuery);
            db.execSQL(userTableSqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);

        onCreate(db);
    }


    //save user detail in database (create new users)
    public Boolean insertUsers(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        Long Result = sqLiteDatabase.insert("Users", null, contentValues);
        if (Result == -1) {
            return false;
        } else {
            return true;
        }
    }


    //Update Password
    public Boolean updatePassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        int Result = sqLiteDatabase.update("Users", contentValues, "email=?", new String[]{email});
        if (Result == -1) {
            return false;
        } else {
            return true;
        }

    }

    //Verify Email for forget Password
    public Boolean checkEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase
                .rawQuery("Select * from users where email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
//check both email and password for forget password
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase
                .rawQuery("Select * from users where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor queryData(String sqlQuery) {
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery(sqlQuery, null);
    }


    //save new Items in Database

    public Boolean insertItem(String name,
                              double price,
                              String description,
                              String image,
                              boolean purchased
    ) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + ITEM_TABLE_NAME + " VALUES (NULL, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindDouble(2, price);
        statement.bindString(3, description);
        statement.bindString(4, image);
        statement.bindLong(5, purchased ? 1 : 0);
        long result = statement.executeInsert();
        database.close();
        return result != -1;
    }

    //get data from database table and column data
    public Cursor getItemById(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sqlQuery = "SELECT * FROM " + ITEM_TABLE_NAME + " WHERE " + ITEM_COLUMN_ID + "=?";
        return database.rawQuery(
                sqlQuery,
                new String[]{String.valueOf(id)}
        );
    }


    //get all Data
    public Cursor getAllItem() {
        SQLiteDatabase database = getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + ITEM_TABLE_NAME;
        return database.rawQuery(sqlQuery, null);
    }


    //Edit Items detail method
    public Boolean update(
            int id,
            String name,
            double price,
            String description,
            String image,
            boolean purchased
    ) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_NAME, name);
        cv.put(ITEM_PRICE, price);
        cv.put(ITEM_DESCRIPTION, description);
        cv.put(ITEM_IMAGE, image);
        cv.put(ITEM_PURCHASED, purchased);
        int result = database.update(ITEM_TABLE_NAME, cv, ITEM_COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        Log.d("Database helper:", "result: " + result);
        database.close();
        return result != -1;
    }


    //delete Items from database
    public void deleteItem(long id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(
                ITEM_TABLE_NAME,
                ITEM_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }
}
