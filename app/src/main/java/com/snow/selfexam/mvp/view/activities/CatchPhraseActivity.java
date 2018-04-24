package com.snow.selfexam.mvp.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.snow.selfexam.R;

public class CatchPhraseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CatchPhraseActivity.class.getSimpleName();
    private Button btCatchPhrase;
    private String[] catchPhraseData = {
            "意志的出现不是对愿望的否定，而是把愿望合并和提升到一个更高的意识水平上。",
            "疼痛的强度，同自然赋于人类的意志和刚度成正比。",
            "不要灰心，不要绝望，对一切都要乐观……需要有决心——这是最要紧的，有了决心一切困难的事都会变得容易。"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_phrase);

        initView();
    }

    private void initView() {
        btCatchPhrase = (Button) findViewById(R.id.bt_catch_phrase);
        btCatchPhrase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_catch_phrase:
                int temp = (int) (Math.random() * catchPhraseData.length);//随机产生一个 1~10 的整数
                break;
        }
    }
}
