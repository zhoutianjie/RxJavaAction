package com.kedacom.rxjavaactiondemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.base.BaseActivity;
import com.kedacom.rxjavaactiondemo.presenter.PollingPresenter;
import com.kedacom.rxjavaactiondemo.view.PollingView;

/**
 * 轮询任务示例
 * Created by zhoutianjie on 2019/4/17.
 */

public class PollingActivity extends BaseActivity implements PollingView{


    private Button mFixBtn;
    private Button mChangeBtn;
    private TextView mFixTv;
    private TextView mChangeTv;
    private StringBuilder fixBuilder;
    private StringBuilder changeBuilder;

    private PollingPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);
        fixBuilder = new StringBuilder();
        changeBuilder = new StringBuilder();
    }



    @Override
    protected void initView() {
        mFixBtn = findViewById(R.id.btn_fix);
        mChangeBtn = findViewById(R.id.btn_change);
        mFixTv = findViewById(R.id.tv_fix);
        mChangeTv = findViewById(R.id.tv_change);
    }

    @Override
    protected void registerListener() {
        mFixBtn.setOnClickListener(v->presenter.startfixPolling());
        mChangeBtn.setOnClickListener(v->presenter.startChangePolling());
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        presenter = new PollingPresenter();
        presenter.attachView(this);
    }

    @Override
    public void updatefixTextView(String msg) {
        fixBuilder.append(msg);
        mFixTv.setText(fixBuilder);
        fixBuilder.append("\n");

    }

    @Override
    public void updateChangeTextView(String msg) {
        changeBuilder.append(msg);
        mChangeTv.setText(changeBuilder);
        changeBuilder.append("\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
