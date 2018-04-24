package com.snow.selfexam.app.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/3/20.
 */

public class UserInfoDBHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userinfo.db";
    private static final int DATABASE_VERSION = 1;

    public UserInfoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //建表
        String sql = "create table person(_id integer primary key autoincrement, name varchar, password varchar, major varchar, login varchar, subject varchar, isDownData varchar, dbpath varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
