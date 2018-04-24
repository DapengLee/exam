package com.snow.selfexam.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Laiyimin on 2016/9/2.
 */
public class ExamDao {

    private Context mContext;
    private SQLiteDatabase db;
    static String tab_name = "accounting_sys_tb";
    static String tab_field01 = "problemid";
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

    public ExamDao(Context context) {
        this.mContext = context;
        this.db = ExamDBHelper.getInstance(context).getReadableDatabase();
    }


    // 根据条件模糊查询数据库数据
    public ArrayList<SearchQuestionBean> query(int top_int, String tab_name,String... str) {
        ArrayList<SearchQuestionBean> result_list = new ArrayList<SearchQuestionBean>();

//模糊查询的三种方式：
/*
* 全部查询
String current_sql_sel = "SELECT  * FROM " + tab_name;
Cursor c = mDatabase.rawQuery(current_sql_sel, null);*/
//1.使用这种query方法%号前不能加' ;
        Cursor c_test = db.query(tab_name, new String[]{tab_field05, tab_field06,tab_field07,tab_field08,tab_field09, tab_field10,tab_field11}, tab_field05 + "  LIKE ? ",
                new String[]{"%" + str[0] + "%"}, null, null, null);
//2.使用这种query方法%号前必须加'  ;
//  Cursor  c_test=mDatabase.query(tab_name, new String[]{tab_field02},tab_field02+"  like '%" + str[0] + "%'", null, null, null, null);
//3.使用这种方式必须在%号前加'  ;
      /*  String current_sql_sel = "SELECT  * FROM " + tab_name + " where " + tab_field02 + " like '%" + str[0] + "%'";*/
//Cursor c_test = mDatabase.rawQuery(current_sql_sel, null);
        Log.e("tag", "查询完成...");
        while (c_test.moveToNext()) {
            SearchQuestionBean searchQuestionBean ;
            String name = c_test.getString(c_test.getColumnIndex(tab_field05));
            String nameA = c_test.getString(c_test.getColumnIndex(tab_field06));
            Log.e("RegionDaotag", name + nameA);
            String nameB = c_test.getString(c_test.getColumnIndex(tab_field07));
            String nameC = c_test.getString(c_test.getColumnIndex(tab_field08));
            String nameD = c_test.getString(c_test.getColumnIndex(tab_field09));
            String nameE = c_test.getString(c_test.getColumnIndex(tab_field10));
            String answerData = c_test.getString(c_test.getColumnIndex(tab_field11));
//name.contains(str[0]);
// 让集合中的数据不重复;
            if (!result_list.contains(name)) {
                /*searchQuestionBean.setDescription(name);
                searchQuestionBean.setQuestionA(nameA);
                searchQuestionBean.setQuestionB(nameB);
                searchQuestionBean.setQuestionC(nameC);
                searchQuestionBean.setQuestionD(nameD);
                searchQuestionBean.setQuestionE(nameE);
                searchQuestionBean.setKnowledgePointAnswer(answerData);*/

                searchQuestionBean = new SearchQuestionBean(name,nameA,nameB,nameC,nameD,nameE,answerData);

                result_list.add(searchQuestionBean);
               // Log.e("tag", name + answerData);
            }
        }
        c_test.close();
        return result_list;
    }
}
