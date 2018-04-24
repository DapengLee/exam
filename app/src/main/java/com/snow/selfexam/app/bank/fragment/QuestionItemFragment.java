package com.snow.selfexam.app.bank.fragment;

import android.app.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.bank.adapter.OptionsListAdapter;
import com.snow.selfexam.app.bank.bean.QuestionBean;
import com.snow.selfexam.app.bank.dao.ExerciseDao;
import com.snow.selfexam.app.bank.view.NoScrollListview;
import com.snow.selfexam.app.db.RegionDao;
import com.snow.selfexam.app.utils.AppSharePreferenceMgr;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class QuestionItemFragment extends Fragment implements OnItemClickListener, OnClickListener {
    QuestionBean questionBean;
    int index;
    private OptionsListAdapter adapter;
    private StringBuffer sb;
    private NoScrollListview lv;
    LocalBroadcastManager mLocalBroadcastManager;
    private LinearLayout ll_choice_back;
    private LinearLayout ll_choice_next;
    private List<QuestionBean> questionlist;
    private String infoType;
    private TextView tv_answer;
    private Button btn_submit;
    private int questionType;
    private String exam;
    private ExerciseDao dao;
    private RegionDao  mRegionDao;
    private String sDataName;



    public QuestionItemFragment(int index, List<QuestionBean> questionlist, String infoType, String exam) {
        this.index = index;
        this.questionlist = questionlist;
        questionBean = questionlist.get(index);
        this.infoType = infoType;
        this.exam=exam;
    }

  /*  public QuestionItemFragment(int index, List<QuestionBean> questionlist, String infoType) {

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.pager_item,
                container, false);
        lv = (NoScrollListview) rootView.findViewById(R.id.lv_options);
        ll_choice_back = (LinearLayout) rootView.findViewById(R.id.ll_choice_back);
        ll_choice_back.setOnClickListener(this);
        ll_choice_next = (LinearLayout) rootView.findViewById(R.id.ll_choice_next);
        ll_choice_next.setOnClickListener(this);


        TextView tv_paper_name = (TextView) rootView.findViewById(R.id.tv_paper_name);
        TextView tv_sequence = (TextView) rootView.findViewById(R.id.tv_sequence);
        TextView tv_total_count = (TextView) rootView.findViewById(R.id.tv_total_count);
        TextView tv_description = (TextView) rootView.findViewById(R.id.tv_description);
        tv_answer = rootView.findViewById(R.id.tv_answer);

        mRegionDao=new RegionDao(getActivity());
        sDataName = mRegionDao.loadTBName(exam);

        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);

        if (infoType.equals("3") || infoType.equals("2")) {

        } else {
            btn_submit.setText("查看答案");
            btn_submit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getActivity(), "查看答案" + questionBean.getAnswer(), Toast.LENGTH_SHORT).show();
                    tv_answer.setVisibility(View.VISIBLE);
                    tv_answer.setText(questionBean.getAnswer());
                }
            });
        }


        adapter = new OptionsListAdapter(getActivity(), questionBean.getQuestionOptions(), lv, index);
        lv.setAdapter(adapter);
        //TODO 展listvie所有子条目开使用了自定义Listview，下面的方法有问题
        //setListViewHeightBasedOnChildren(lv);

        tv_paper_name.setText(exam);
        tv_sequence.setText((index + 1) + "");
        tv_total_count.setText("/" + questionlist.size());
        //(Boolean) AppSharePreferenceMgr.get(getActivity(), "isDownDataLoader", false);


        tv_description.setText(questionBean.getDescription());

        //题干描述前面加上(单选题)或(多选题)
        //判断是单选还是多选
        questionType = questionBean.getQuestionType();
        sb = new StringBuffer();
        if (questionType == 1) {//单选
            SpannableStringBuilder ssb = new SpannableStringBuilder("(单选题)");
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(questionBean.getDescription());
            tv_description.setText(ssb);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.notifyDataSetChanged();
                    //TODO 单选题选中后自动跳转到下一页
                   /* Intent intent = new Intent("com.leyikao.jumptonext");
                    mLocalBroadcastManager.sendBroadcast(intent);*/

                }
            });
            btn_submit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {


                    long[] ids = lv.getCheckedItemIds();
                    for (int i = 0; i < ids.length; i++) {
                        long id = ids[i];
                        sb.append(questionBean.getQuestionOptions().get((int) id).getName()).append(" ");
                    }
                    if (questionBean.getAnswer().trim().equals(sb.toString().trim())) {
                        Intent intent = new Intent("com.leyikao.jumptonext");
                        mLocalBroadcastManager.sendBroadcast(intent);
                    } else {
                       Toast.makeText(getActivity(), "答案选项" + questionBean.getAnswer(), Toast.LENGTH_SHORT).show();
                        //  EventBus.getDefault().postSticky(questionBean);
                    }



                    sb.setLength(0);


                }
            });
        } else if (questionType == 2) {//多选
            SpannableStringBuilder ssb = new SpannableStringBuilder("(多选题)");
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(questionBean.getDescription());
            tv_description.setText(ssb);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    adapter.notifyDataSetChanged();
                }
            });
            btn_submit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    long[] ids = lv.getCheckedItemIds();
                    for (int i = 0; i < ids.length; i++) {
                        long id = ids[i];
                        sb.append(questionBean.getQuestionOptions().get((int) id).getName()).append(" ");
                    }
                    Toast.makeText(getActivity(), "答案选项" + questionBean.getAnswer(), Toast.LENGTH_SHORT).show();
                    sb.setLength(0);
                }
            });
        }


        return rootView;
    }

    //	private void setListViewHeightBasedOnChildren(ListView listView) {
//		BaseAdapter adapter = (BaseAdapter) listView.getAdapter();   
//        if (adapter == null) {  
//        	Log.e("执行listview展开", "为空");
//            return;  
//        }  
//  
//        int totalHeight = 0;  
//        for (int i = 0; i < adapter.getCount(); i++) {  
//            View listItem = adapter.getView(i, null, listView);  
//            listItem.measure(0, 0);  
//            totalHeight += listItem.getMeasuredHeight();  
//        }  
//  
//        ViewGroup.LayoutParams params = listView.getLayoutParams();  
//        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));  
//        listView.setLayoutParams(params);  
//        Log.e("执行listview展开", "执行listview展开");
//		
//	}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), questionBean.toString() + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_choice_back:




                //  Toast.makeText(getActivity(), "上一步", Toast.LENGTH_SHORT).show();
                Intent intentBack = new Intent("com.leyikao.jumptoback");
                mLocalBroadcastManager.sendBroadcast(intentBack);
                break;
            case R.id.ll_choice_next:


                dao=new ExerciseDao(getActivity());
                String errCode = dao.testQueryIsDo(questionBean.getQuestionId(), sDataName);
                System.out.println(errCode);
                if (errCode.equals("")){
                    dao.insertDo(questionBean.getQuestionId(), sDataName);
                }
                if (questionType == 1 || questionType==2) {//单选
                    adapter.notifyDataSetChanged();
                    long[] ids = lv.getCheckedItemIds();
                    for (int i = 0; i < ids.length; i++) {
                        long id = ids[i];
                        sb.append(questionBean.getQuestionOptions().get((int) id).getName()).append(" ");
                    }
                    if (sb.toString().equals("")) {
                        Toast.makeText(getActivity(), "请选择答案", Toast.LENGTH_SHORT).show();
                    } else {
                        if (questionBean.getAnswer().equals(sb.toString().trim())) {
                            Intent intent = new Intent("com.leyikao.jumptonext");
                          //  Toast.makeText(getActivity(), "恭喜您答对了" + questionBean.getAnswer(), Toast.LENGTH_SHORT).show();
                            mLocalBroadcastManager.sendBroadcast(intent);
                        } else {
                          //  Toast.makeText(getActivity(), "答案选项" + questionBean.getAnswer(), Toast.LENGTH_SHORT).show();

                            new AlertDialog.Builder(getActivity())

                                    .setTitle("答错了！")
                                   .setCancelable(false)
                                    .setMessage("题目  "+questionBean.getDescription()+"\n"+"答案选项为   "+ questionBean.getAnswer())

                                    .setPositiveButton("进入下一题练习", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            EventBus.getDefault().postSticky(questionBean);
                                            //TODO 单选题选中后自动跳转到下一页*/
                                            Intent intent = new Intent("com.leyikao.jumptonext");
                                            mLocalBroadcastManager.sendBroadcast(intent);
                                        }
                                    }).create().show();
                        }
                     /* ChoiceActivity activity = (ChoiceActivity) getActivity();
                    int currentPosition = activity.vp.getCurrentItem() + 1;
                    Toast.makeText(getActivity(),"下一步"+activity.vp.getCurrentItem()+1,Toast.LENGTH_SHORT).show();

                  /*  if (currentPosition==ChoiceActivity.questionlist.size()){
                        ll_choice_next.setVisibility(View.INVISIBLE);
                    }*/



                    }
                } else {


                    Intent intent = new Intent("com.leyikao.jumptonext");
                    mLocalBroadcastManager.sendBroadcast(intent);
                }
                break;
        }
    }
}