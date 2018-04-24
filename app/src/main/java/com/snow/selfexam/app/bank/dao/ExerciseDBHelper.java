package com.snow.selfexam.app.bank.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 地区数据库
 * Created by Laiyimin on 2016/8/8.
 */
public class ExerciseDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ks.db";
    private static final int DB_VERSION = 1;

    private static ExerciseDBHelper helper;

    public static ExerciseDBHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (ExerciseDBHelper.class) {
                if (helper == null) {
                    helper = new ExerciseDBHelper(context.getApplicationContext());
                }
            }
        }
        return helper;
    }


    /**
     * 关闭数据库, 用在退出登录时
     */
    public static void closeDb() {
        if (helper != null)
            helper.close();
        helper = null;
    }

    public ExerciseDBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public ExerciseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
