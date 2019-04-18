package com.kedacom.rxjavaactiondemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        registerListener();
        initPresenter();

    }


    protected abstract void initView();
    protected abstract void registerListener();
    protected void initPresenter(){

    }
}
