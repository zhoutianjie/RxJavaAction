package com.kedacom.rxjavaactiondemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.activity.news.NewsActivity;
import com.kedacom.rxjavaactiondemo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private Button mUiUptateBtn;
    private Button mBufferBtn;
    private Button mSearchBtn;
    private Button mRetrofit;
    private Button mPollingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mUiUptateBtn = findViewById(R.id.ui_update);
        mBufferBtn = findViewById(R.id.buffer);
        mSearchBtn = findViewById(R.id.search);
        mRetrofit = findViewById(R.id.retrofit);
        mPollingBtn = findViewById(R.id.polling);
    }

    @Override
    protected void registerListener() {
        mUiUptateBtn.setOnClickListener(v->startActivity(new Intent(this,NotifyUIExampleActivity.class)));
        mBufferBtn.setOnClickListener(v->startActivity(new Intent(this,BufferActivity.class)));
        mSearchBtn.setOnClickListener(v->startActivity(new Intent(this,SearchActivity.class)));
        mRetrofit.setOnClickListener(v->startActivity(new Intent(this, NewsActivity.class)));
        mPollingBtn.setOnClickListener(v->startActivity(new Intent(this,PollingActivity.class)));
    }
}
