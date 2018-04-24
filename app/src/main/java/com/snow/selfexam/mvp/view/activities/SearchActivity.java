package com.snow.selfexam.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snow.selfexam.R;
import com.snow.selfexam.app.base.BaseActivity;
import com.snow.selfexam.app.db.RegionDao;
import com.snow.selfexam.app.db.SearchHistorysBean;
import com.snow.selfexam.app.db.SearchHistorysDao;
import com.snow.selfexam.app.utils.ToastUtil;
import com.snow.selfexam.mvp.modle.SearchHelperBean;
import com.snow.selfexam.mvp.presenter.SearchPresenter;
import com.snow.selfexam.mvp.view.weight.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity<SearchPresenter> implements OnClickListener {

    private ClearEditText et_search_keyword;
    private Button btn_search;
    private TextView tv_back;
    private TextView tv_clear;
    private LinearLayout ll_content;
    private ListView lv_history_word;

    private SearchHistorysDao dao;
    private ArrayList<SearchHistorysBean> historywordsList = new ArrayList<SearchHistorysBean>();
    private SearchHistoryAdapter mAdapter;
    private int count;
    private String info;
    private String sDataName;
    private TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EventBus.getDefault().register(this);
        //查找专业对应的表

        dao = new SearchHistorysDao(this);
        historywordsList = dao.findAll();
        RegionDao daoData=new RegionDao(this);
        //后期把表填写完整
        sDataName = daoData.loadTBName(info);
        //Toast.makeText(this, sDataName,Toast.LENGTH_SHORT).show();
        initView();
    }

    private void initView() {
        et_search_keyword = (ClearEditText) findViewById(R.id.et_search_keyword);

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(this);
        titleName=findViewById(R.id.tv_title_name);

        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        lv_history_word = (ListView) findViewById(R.id.lv_history_word);
        mAdapter = new SearchHistoryAdapter(historywordsList);
        titleName.setVisibility(View.VISIBLE);
        titleName.setText(info);
        count = mAdapter.getCount();
        if (count > 0) {

            tv_clear.setVisibility(View.VISIBLE);
        } else {
            tv_clear.setVisibility(View.GONE);
        }
        lv_history_word.setAdapter(mAdapter);
        lv_history_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SearchHistorysBean bean = (SearchHistorysBean) mAdapter.getItem(position);

                et_search_keyword.setText(bean.historyword);

                mAdapter.notifyDataSetChanged();
                btn_search.performClick();
            }
        });

    }
    // 3 接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus(String info) {
        this.info=info;
        Toast.makeText(this, "" + info, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_search:
                String searchWord = et_search_keyword.getText().toString().trim();
                if (TextUtils.isEmpty(searchWord)) {
                    ToastUtil.showToast(this,"搜索的内容不能为空");
                } else {

                    dao.addOrUpdate(searchWord);
                    historywordsList.clear();
                    historywordsList.addAll(dao.findAll());
                    mAdapter.notifyDataSetChanged();
                    tv_clear.setVisibility(View.VISIBLE);


                    Intent intent = new Intent(this, SearchDetailActivity.class);
                    String word = et_search_keyword.getText().toString().trim();

                    //表名
                    EventBus.getDefault().postSticky(new SearchHelperBean(sDataName,word));
                    startActivity(intent);

                }

                break;
            case R.id.tv_clear:
                dao.deleteAll();
                historywordsList.clear();
                mAdapter.notifyDataSetChanged();
                tv_clear.setVisibility(View.GONE);
                break;
            default:
                break;

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

    class SearchHistoryAdapter extends BaseAdapter {

        private ArrayList<SearchHistorysBean> historywordsList;

        public SearchHistoryAdapter(ArrayList<SearchHistorysBean> historywordsList) {
            this.historywordsList = historywordsList;
        }

        @Override
        public int getCount() {

            return historywordsList == null ? 0 : historywordsList.size();
        }

        @Override
        public Object getItem(int position) {

            return historywordsList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_history_word, null);
                holder.tv_word = (TextView) convertView.findViewById(R.id.tv_search_record);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_word.setText(historywordsList.get(position).toString());

            return convertView;
        }
    }
    class ViewHolder {
        TextView tv_word;
    }
}
