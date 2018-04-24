package com.snow.selfexam.mvp.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.snow.selfexam.R;

import org.angmarch.views.NiceSpinner;

public class ErrorActivity extends AppCompatActivity {

    private NiceSpinner error_nice_spinner;
    private TextView tv_error_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        initView();
    }

    private void initView() {
        error_nice_spinner = (NiceSpinner) findViewById(R.id.error_nice_spinner);
        tv_error_time = (TextView) findViewById(R.id.tv_error_time);
    }
}
