package com.snow.selfexam.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 地区数据库
 * Created by Laiyimin on 2016/8/8.
 */
public class ExamDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ks.db";
    private static final int DB_VERSION = 1;

    private static ExamDBHelper helper;

    public static ExamDBHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (ExamDBHelper.class) {
                if (helper == null) {
                    helper = new ExamDBHelper(context.getApplicationContext());
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

    public ExamDBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public ExamDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
