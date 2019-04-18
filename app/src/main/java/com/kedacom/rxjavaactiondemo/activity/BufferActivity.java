package com.kedacom.rxjavaactiondemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.base.BaseActivity;
import com.kedacom.rxjavaactiondemo.presenter.BufferPresenter;
import com.kedacom.rxjavaactiondemo.view.BufferView;

/**
 * Buffer 操作符示例 求平均值
 * 每隔一个时间窗口拿到数据然后求平均值
 * Created by zhoutianjie on 2019/4/16.
 */

public class BufferActivity extends BaseActivity implements BufferView{

    private BufferPresenter presenter;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
    }

    @Override
    protected void initPresenter() {
        presenter = new BufferPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void initView() {
        textView = findViewById(R.id.tv_show);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void updateUI(double tempture) {
        textView.setText(""+tempture);
    }
}
