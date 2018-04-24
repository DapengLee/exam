package com.snow.selfexam.app.db;

/**
 * Created by Administrator on 2018/3/28.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

public class DBManage extends SQLiteOpenHelper {
    static int init_version = 1;
    static String database_name = "android_sqlite_test.db";
    static String tab_name = "uer_log";
    static String tab_field01 = "_id";
    static String tab_field02 = "problemtype";
    static String tab_field03 = "specialtyid";
    static String tab_field04 = "subjectid";
    static String tab_field05 = "problems";
    static String tab_field06 = "optionA";
    static String tab_field07 = "optionB";
    static String tab_field08 = "optionC";
    static String tab_field09 = "optionD";
    static String tab_field10 = "optionE";
    static String tab_field11 = "answer";
    SQLiteDatabase mDatabase;

    public DBManage(Context context) {
        super(context, database_name, null, init_version);
// TODO Auto-generated constructor stub
        mDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        String sql = "create table " + tab_name + " ( " + tab_field01
                + " integer primary key , " + tab_field02 + " text  not null, " + tab_field03 + " text  not null, " + tab_field04 + " text  not null, " + tab_field05 + " text  not null, " + tab_field06 + " text  not null, " + tab_field07 + " text  not null, " + tab_field08 + " text  not null, " + tab_field09 + " text  not null, " + tab_field10 + " text  not null, " + tab_field11 + " text  not null) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
    }

    /**
     * 插入记录
     * 2	5	51	7英国实行的社会行政体制属于（B）	A.自由主义福利体制	B.社会民主主义福利体制	C.保守主义福利体制	D.激进主义福利体制
     *
     * @return
     */
    public void insertDataTest() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(tab_field02, "2");
        contentValues.put(tab_field03, "5");
        contentValues.put(tab_field04, "51");
        contentValues.put(tab_field05, "英国实行的社会行政体制属于（B）");
        contentValues.put(tab_field06, "A.自由主义福利体制");
        contentValues.put(tab_field07, "B.社会民主主义福利体制");
        contentValues.put(tab_field08, "C.保守主义福利体制");
        contentValues.put(tab_field09, "D.保守主义福利体制");
        contentValues.put(tab_field10, "E.激进主义福利体制");
        contentValues.put(tab_field11, "E.激进主义福利体制");
        mDatabase.insert(tab_name, null, contentValues);
    };


    public boolean insertData(String... str) {
        int request_int = 0;
        for (int i = 0; i < str.length; i++) {
// 实例化一个ContentValues 对象 ,作用，收集数据，方便于SQLite执行增，删，改，查
            ContentValues contentValues = new ContentValues();
            contentValues.put(tab_field02, str[i]);
            mDatabase.insert(tab_name, null, contentValues);
            request_int++;
        }
        return str.length == request_int;
    }


    // 根据条件模糊查询数据库数据
    public ArrayList<SearchQuestionBean> query(int top_int, String... str) {
        ArrayList<SearchQuestionBean> result_list = new ArrayList<SearchQuestionBean>();
        mDatabase = getReadableDatabase();
//模糊查询的三种方式：
/*
* 全部查询
String current_sql_sel = "SELECT  * FROM " + tab_name;
Cursor c = mDatabase.rawQuery(current_sql_sel, null);*/
//1.使用这种query方法%号前不能加' ;
        Cursor c_test = mDatabase.query(tab_name, new String[]{tab_field05,tab_field06,tab_field07}, tab_field05 + "  LIKE ? ",
                new String[]{"%" + str[0] + "%"}, null, null, null);
//2.使用这种query方法%号前必须加'  ;
//  Cursor  c_test=mDatabase.query(tab_name, new String[]{tab_field02},tab_field02+"  like '%" + str[0] + "%'", null, null, null, null);
//3.使用这种方式必须在%号前加'  ;
        String current_sql_sel = "SELECT  * FROM " + tab_name + " where " + tab_field02 + " like '%" + str[0] + "%'";
//Cursor c_test = mDatabase.rawQuery(current_sql_sel, null);
        Log.e("tag", "查询完成...");
        while (c_test.moveToNext()) {
            SearchQuestionBean searchQuestionBean=new SearchQuestionBean();
            String name = c_test.getString(c_test.getColumnIndex(tab_field05));
            String answerData = c_test.getString(c_test.getColumnIndex(tab_field06));
//name.contains(str[0]);
// 让集合中的数据不重复;
            if (!result_list.contains(name)) {
                searchQuestionBean.setDescription(name);
                searchQuestionBean.setKnowledgePointAnswer(answerData);
                result_list.add(searchQuestionBean);
                Log.e("tag", name+answerData);
            }
        }
        c_test.close();
        return result_list;
    }
}