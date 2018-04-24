package com.snow.selfexam.app.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snow.selfexam.app.bank.bean.QuestionBean;
import com.snow.selfexam.app.bank.bean.QuestionOptionBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 连接数据库
 * Created by LGL on 2016/6/4.
 */
public class DBService {

    private SQLiteDatabase db;
    private QuestionBean question;
    private QuestionOptionBean option;
    private QuestionBean question1;

    //构造方法
    public DBService() {
        //连接数据库
        db = SQLiteDatabase.openDatabase("/data/data/com.example.listmultichoise/databases/question.db", null, SQLiteDatabase.OPEN_READWRITE);
    }

    //获取数据库的数据
    public List<QuestionBean> getQuestion() {
        List<QuestionBean> list = new ArrayList<QuestionBean>();
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                List<QuestionOptionBean> options = new ArrayList<QuestionOptionBean>();
                option = new QuestionOptionBean();
                question1 = new QuestionBean();
                //ID
                question1.setQuestionId(cursor.getInt(cursor.getColumnIndex("Field1")) + "");
                //问题
                question1.setDescription(cursor.getString(cursor.getColumnIndex("Field2")));
                //四个选择
                option = new QuestionOptionBean();
                option.setName("A");
                option.setDescription(cursor.getString(cursor.getColumnIndex("Field3")));
                options.add(option);

                option = new QuestionOptionBean();
                option.setName("B");
                option.setDescription(cursor.getString(cursor.getColumnIndex("Field4")));
                options.add(option);

                option = new QuestionOptionBean();
                option.setName("C");
                option.setDescription(cursor.getString(cursor.getColumnIndex("Field5")));
                options.add(option);

                option = new QuestionOptionBean();
                option.setName("D");
                option.setDescription(cursor.getString(cursor.getColumnIndex("Field6")));
                options.add(option);

             /*   question.answerD = cursor.getString(cursor.getColumnIndex("Field6"));
                //答案
                question.answer = cursor.getInt(cursor.getColumnIndex("Field7"));
                //解析
                question.explaination = cursor.getString(cursor.getColumnIndex("Field8"));
                //设置为没有选择任何选项
                question.selectedAnswer = -1;*/
                question1.setQuestionOptions(options);
                list.add(question1);
            }
        }
        return list;
    }
}
