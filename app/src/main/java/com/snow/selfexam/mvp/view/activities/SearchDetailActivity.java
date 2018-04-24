package com.snow.selfexam.mvp.view.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.snow.selfexam.R;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.db.ExamDao;
import com.snow.selfexam.app.db.SearchQuestionBean;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.SearchHelperBean;
import com.snow.selfexam.mvp.presenter.SearchDetail;
import com.snow.selfexam.mvp.view.weight.MyTextView;
import com.snow.selfexam.mvp.view.weight.QuickSearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchDetailActivity extends BaseActivity<SearchDetail> {

    private TextView tv_back;
    private ExamDao dao;
    private ListView mListView;
    private List<SearchQuestionBean> data;
    private QuickSearchView mQuickSearchView;//旁边的字母
    private TextView wordTextView;

    private String keyWord;
    private String tbName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        EventBus.getDefault().register(this);
        tv_back = (TextView) findViewById(R.id.tv_back);
        mListView = (ListView) findViewById(R.id.lv_view);
        wordTextView = findViewById(R.id.art_manger_word);
        mQuickSearchView = (QuickSearchView) findViewById(R.id.art_manger_qsv);
        //设置监听(监听触摸相应的字母  QuickSearchView里面自定义的监听)
        mQuickSearchView.setOnWordLinaever(new QuickSearchView.OnWordLinaever() {
                                               @Override
                                               public void onWord(String word) {
                                                   updateWord(word);
                                                   scrollListView(word);
                                               }
                                           }
        );
        data = new ArrayList<>();
        dao = new ExamDao(this);


        data = dao.query(1, tbName, keyWord);

		/*for (int i = 0; i < 5; i++) {
			list.add("数据"+i);
		}*/

        if (data.size() == 0) {
            ToastUtil.showToast(this, "没有搜索到相关的数据");
        }

        //mListView.setAdapter(new  DBAdapter());
        Collections.sort(data);
        mListView.setAdapter(new DBAdapter());
        tv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(SearchHelperBean searchInfo) {
        keyWord = searchInfo.getKeyWord();
        tbName = searchInfo.getTbName();
    }

    /**
     * 实现点击相应字母两秒出现
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 2) {
                wordTextView.setVisibility(View.GONE);
            }
        }
    };

    /**
     * word延迟两秒之后消失
     *
     * @param word 点击到的word与页面出现的word相对应
     */
    private void updateWord(String word) {
        wordTextView.setVisibility(View.VISIBLE);
        wordTextView.setText(word);


        handler.removeMessages(2);

        handler.sendEmptyMessageDelayed(2, 2000);
    }

    /**
     * 触摸到哪个字母listview就会定位到相应的字母
     *
     * @param word 触摸到的字母
     */

    protected void scrollListView(String word) {

        for (int i = 0; i < data.size(); i++) {
            //截取每一个item数据的第一个拼音
            boolean equal = data.get(i).pinyin.substring(0, 1).equals(word);
            if (equal) {
                //定位到截取拼音首字母的位置
                mListView.setSelection(i);
                return;
            }
        }
    }

    @Override
    public int setView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setListener(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }


    private class DBAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(SearchDetailActivity.this, R.layout.item_search_db, null);
            MyTextView tvName = (MyTextView) inflate.findViewById(R.id.book_name);
            TextView tv_item_word = (TextView) inflate.findViewById(R.id.tv_item_word);
            TextView ansewerName = (TextView) inflate.findViewById(R.id.answer_name);
            TextView ansewerNameA = (TextView) inflate.findViewById(R.id.a_name);
            TextView ansewerNameB = (TextView) inflate.findViewById(R.id.b_name);
            TextView ansewerNameC = (TextView) inflate.findViewById(R.id.c_name);
            TextView ansewerNameD = (TextView) inflate.findViewById(R.id.d_name);
            TextView ansewerNameE = (TextView) inflate.findViewById(R.id.e_name);


            tvName.setSpecifiedTextsColor(data.get(position).getDescription(), keyWord, Color.parseColor("#00bcfc"));
            //tvName.setText(data.get(position).getDescription());

            //装配listview的item
            SearchQuestionBean searchQuestionBean = data.get(position);
            String word = searchQuestionBean.pinyin.substring(0, 1);

            ansewerName.setText("  答案    ：" + data.get(position).getKnowledgePointAnswer());
            if (data.get(position).getQuestionA().equals("")) {
                ansewerNameA.setVisibility(View.GONE);
            } else {
                ansewerNameA.setText(data.get(position).getQuestionA());
            }

            if (data.get(position).getQuestionB().equals("")) {
                ansewerNameB.setVisibility(View.GONE);
            } else {
                ansewerNameB.setText(data.get(position).getQuestionB());
            }

            if (data.get(position).getQuestionC().equals("")) {
                ansewerNameC.setVisibility(View.GONE);
            } else {
                ansewerNameC.setText(data.get(position).getQuestionC());
            }

            if (data.get(position).getQuestionD().equals("")) {
                ansewerNameD.setVisibility(View.GONE);
            } else {
                ansewerNameD.setText(data.get(position).getQuestionD());
            }

            if (data.get(position).getQuestionE().equals("")) {
                ansewerNameE.setVisibility(View.GONE);
            } else {
                ansewerNameE.setText(data.get(position).getQuestionE());
            }
            //实现按position的装配(按拼音进行了相关的排序进行分类)
            if (position == 0) {
                tv_item_word.setVisibility(View.GONE);
                //  holder.setVisibility(R.id.tv_item_word, View.VISIBLE);
                tv_item_word.setText(word);
            } else {
                String preWord = data.get(position - 1).pinyin.substring(0, 1);

                if (word.equals(preWord)) {
                    tv_item_word.setVisibility(View.GONE);
                    // holder.setVisibility(R.id.tv_item_word, View.GONE);
                } else {
                    //   holder.setVisibility(R.id.tv_item_word, View.VISIBLE);
                    tv_item_word.setVisibility(View.GONE);
                    tv_item_word.setText(word);
                }

            }
            return inflate;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
