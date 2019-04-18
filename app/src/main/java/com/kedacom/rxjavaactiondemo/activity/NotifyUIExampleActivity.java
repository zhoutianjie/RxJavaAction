package com.kedacom.rxjavaactiondemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.base.BaseActivity;
import com.kedacom.rxjavaactiondemo.presenter.NotifyUIPresent;
import com.kedacom.rxjavaactiondemo.view.NotifyUIView;

/**
 * 后台耗时操作,更新UI示例
 * Created by zhoutianjie on 2019/4/16.
 */

public class NotifyUIExampleActivity extends BaseActivity implements NotifyUIView{

    private Button mStartBtn;
    private TextView mPercentTv;
    private ProgressBar mProgressPb;


    private NotifyUIPresent present;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifyui);
    }

    @Override
    protected void initView() {
        mStartBtn = findViewById(R.id.btn_start);
        mProgressPb = findViewById(R.id.pb_progress);
        mPercentTv = findViewById(R.id.tv_percent);
    }

    @Override
    protected void registerListener() {
        mStartBtn.setOnClickListener(v->{mStartBtn.setClickable(false);
            present.startDownLoad();});
    }

    @Override
    protected void initPresenter() {
        present = new NotifyUIPresent();
        present.attachView(this);
    }

    @Override
    public void updateProgress(int percent) {
        mPercentTv.setText("进度 ："+percent+"%");
        mProgressPb.setProgress(percent);
    }

    @Override
    public void complete() {
        mPercentTv.setText("下载成功");
        mProgressPb.setProgress(100);
        mStartBtn.setClickable(true);
    }

    @Override
    public void downFailed(String e) {
        mPercentTv.setText("下载失败");
        mProgressPb.setProgress(0);
        mStartBtn.setClickable(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.onDetach();
    }
}
