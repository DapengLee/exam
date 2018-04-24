package com.snow.selfexam.app.bank.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snow.selfexam.app.bank.bean.QuestionBean;
import com.snow.selfexam.app.bank.bean.QuestionOptionBean;
import com.snow.selfexam.app.db.ExamDBHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Laiyimin on 2016/9/2.
 */
public class ExerciseDao {

    private Context mContext;
    private SQLiteDatabase db;
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
    static String tab_field12 = "error";


    public List<QuestionBean> questionlist = new ArrayList<QuestionBean>();
    QuestionOptionBean option;

    public QuestionBean question;

    public ExerciseDao(Context context) {
        this.mContext = context;
        this.db = ExamDBHelper.getInstance(context).getReadableDatabase();
    }


    public List<QuestionBean> testQuery(String problemType, String tab_name) {

        // 2. 执行query select * from person
        //SELECT * FROM 表 WHERE problemtype = 4 ORDER BY  RAND() LIMIT 10
        Cursor c_test = db.query(tab_name, null, "problemtype=?", new String[]{problemType}, null, null, "RANDOM() limit 10");
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数
        int count = c_test.getCount();

        //取出cursor中所有的数据
        while (c_test.moveToNext()) {
            List<QuestionOptionBean> options = new ArrayList<QuestionOptionBean>();
            String problemid = c_test.getString(c_test.getColumnIndex(tab_field01));
            String name = c_test.getString(c_test.getColumnIndex(tab_field05));


            if (problemType.equals("3") || problemType.equals("2")) {
                String nameA = c_test.getString(c_test.getColumnIndex(tab_field06));
                option = new QuestionOptionBean("A", nameA);
                options.add(option);

                String nameB = c_test.getString(c_test.getColumnIndex(tab_field07));
                option = new QuestionOptionBean("B", nameB);
                options.add(option);

                String nameC = c_test.getString(c_test.getColumnIndex(tab_field08));
                option = new QuestionOptionBean("C", nameC);
                options.add(option);

                String nameD = c_test.getString(c_test.getColumnIndex(tab_field09));
                option = new QuestionOptionBean("D", nameD);
                options.add(option);

                if (problemType.equals("3")) {
                    String nameE = c_test.getString(c_test.getColumnIndex(tab_field10));
                    option = new QuestionOptionBean("E", nameE);
                    options.add(option);
                }
            }
            String answerData = c_test.getString(c_test.getColumnIndex(tab_field11));

            if (problemType.equals("2")) {
                question = new QuestionBean(problemid, name, Integer.parseInt(problemType) - 1, "常识判断", "001", options, answerData);
            } else if (problemType.equals("3")) {
                question = new QuestionBean(problemid, name, Integer.parseInt(problemType) - 1, "常识判断", "001", options, answerData);
            } else {
                question = new QuestionBean(problemid, name, Integer.parseInt(problemType) - 1, "常识判断", "001", options, answerData);
            }

            questionlist.add(question);
        }
        // 3. 关闭
        c_test.close();
        db.close();
        // 4. 提示
        // Toast.makeText(mContext, "count=" + count, Toast.LENGTH_SHORT).show();
        return questionlist;
    }

    public List<QuestionBean> testQuery(String typeData, String isJiXu, String tab_name,String s) {
        Cursor c_test;
        if (isJiXu.equals("")) {
            // 2. 执行query select * from person
            //SELECT * FROM 表 WHERE problemtype = 4 ORDER BY  RAND() LIMIT 10
            c_test = db.query(tab_name, null, "problemtype=?", new String[]{typeData}, null, null, "RANDOM() limit 10");
        } else {
            // 2. 执行query select * from person
            //SELECT * FROM 表 WHERE problemtype = 4 ORDER BY  RAND() LIMIT 10  "条件1=? and 条件2=?"
            c_test = db.query(tab_name, null, "problemtype=? and error=?", new String[]{typeData,s}, null, null, null);
        }

        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数
        int count = c_test.getCount();

        //取出cursor中所有的数据
        while (c_test.moveToNext()) {
            List<QuestionOptionBean> options = new ArrayList<QuestionOptionBean>();

            String problemid = c_test.getString(c_test.getColumnIndex(tab_field01));
            String name = c_test.getString(c_test.getColumnIndex(tab_field05));


            if (typeData.equals("3") || typeData.equals("2")) {


                String nameA = c_test.getString(c_test.getColumnIndex(tab_field06));
                option = new QuestionOptionBean("A", nameA);
                options.add(option);

                String nameB = c_test.getString(c_test.getColumnIndex(tab_field07));
                option = new QuestionOptionBean("B", nameB);
                options.add(option);

                String nameC = c_test.getString(c_test.getColumnIndex(tab_field08));
                option = new QuestionOptionBean("C", nameC);
                options.add(option);

                String nameD = c_test.getString(c_test.getColumnIndex(tab_field09));
                option = new QuestionOptionBean("D", nameD);
                options.add(option);

                if (typeData.equals("3")) {
                    String nameE = c_test.getString(c_test.getColumnIndex(tab_field10));
                    option = new QuestionOptionBean("E", nameE);
                    options.add(option);
                }

            }


            if (typeData.equals("2")) {
                String answerData = c_test.getString(c_test.getColumnIndex(tab_field11)).replaceAll("\\d+", "").replace(".", "").trim();
                question = new QuestionBean(problemid, name, Integer.parseInt(typeData) - 1, "常识判断", "001", options, answerData);

            } else if (typeData.equals("3")) {
                String answerData = c_test.getString(c_test.getColumnIndex(tab_field11)).replaceAll("\\d+", "").replace(".", "").trim();
                question = new QuestionBean(problemid, name, Integer.parseInt(typeData) - 1, "常识判断", "001", options, answerData);
            } else {
                String answerData = c_test.getString(c_test.getColumnIndex(tab_field11));
                question = new QuestionBean(problemid, name, Integer.parseInt(typeData) - 1, "常识判断", "001", options, answerData);
            }

            questionlist.add(question);
        }
        // 3. 关闭
        c_test.close();
        db.close();
        // 4. 提示
        // Toast.makeText(mContext, "count=" + count, Toast.LENGTH_SHORT).show();
        return questionlist;
    }

    public String testQueryIsDo(String questionId, String sDataName) {

        String errCode;

        Cursor c_test = db.query(sDataName, null, "problemid=?", new String[]{questionId}, null, null, "RANDOM() limit 10");
        //cursor = database.query("person", null, "_id=?", new String[]{"3"}, null, null, null);
        //得到匹配的总记录数
        int count = c_test.getCount();

        //取出cursor中所有的数据
        while (c_test.moveToNext()) {

            errCode = c_test.getString(c_test.getColumnIndex(tab_field12));

            return errCode;
        }
        // 3. 关闭
        c_test.close();
        db.close();
        //
        // Toast.makeText(mContext, "count=" + count, Toast.LENGTH_SHORT).show();
        return "2";
    }

    public void insertDo(String questionId, String sDataName) {
        //2. 执行insert  insert into person(name, age) values('Tom', 12)
        ContentValues values = new ContentValues();
        values.put(tab_field12, "1");
        int updateCount = db.update(sDataName, values, "problemid=?", new String[]{questionId});
        db.close();
    }
}
