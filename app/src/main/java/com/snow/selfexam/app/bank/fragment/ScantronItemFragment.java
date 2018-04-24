package com.snow.selfexam.app.bank.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snow.selfexam.R;
import com.snow.selfexam.app.bank.bean.QuestionBean;
import com.snow.selfexam.app.utils.UIUtils;

import java.util.List;
import java.util.Set;


public class ScantronItemFragment extends Fragment {
    LocalBroadcastManager mLocalBroadcastManager;

    int count;
    int[] mIds;
    private List<QuestionBean> questionlist;
    private Set<QuestionBean> errQuestionData;

    public ScantronItemFragment(List<QuestionBean> questionlist, Set<QuestionBean> errQuestionData, String exam) {
        this.errQuestionData = errQuestionData;
        this.questionlist = questionlist;
        count = questionlist.size();
        mIds = new int[count];
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.activity_report,
                container, false);
        GridView gv = (GridView) rootView.findViewById(R.id.report_gv);
        TextView tv_report_total_question = (TextView) rootView.findViewById(R.id.tv_report_total_question);
        TextView err_tv = (TextView) rootView.findViewById(R.id.err_tv);
        err_tv.setText(errQuestionData.size() + "");
        TextView tv_report_exam_type = (TextView) rootView.findViewById(R.id.tv_report_exam_type);
        RelativeLayout rl_result_panel = (RelativeLayout) rootView.findViewById(R.id.rl_result_panel);
        //设置scrollview 自动置顶
        rl_result_panel.setFocusable(true);
        rl_result_panel.setFocusableInTouchMode(true);
        rl_result_panel.requestFocus();

        tv_report_total_question.setText("道/" + count + "道");
        MyAdapter adapter = new MyAdapter(UIUtils.getContext());
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO跳转到相应的viewpager 页面
                Intent intent = new Intent("com.leyikao.jumptopage");
                intent.putExtra("index", position);
                mLocalBroadcastManager.sendBroadcast(intent);
            }
        });

        return rootView;
    }

    private class MyAdapter extends BaseAdapter {
        private Context mContext;

        public MyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(mContext);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridView.LayoutParams(70, 70));
            tv.setPadding(8, 8, 8, 8);

            tv.setText(mIds[position] + "");
            tv.setBackgroundResource(R.drawable.option_btn_single_normal);
            return tv;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}