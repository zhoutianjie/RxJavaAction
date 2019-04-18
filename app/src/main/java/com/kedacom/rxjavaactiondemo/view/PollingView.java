package com.kedacom.rxjavaactiondemo.view;

import com.kedacom.rxjavaactiondemo.base.BaseView;

/**
 * Created by zhoutianjie on 2019/4/17.
 */

public interface PollingView extends BaseView {
    void updatefixTextView(String msg);
    void updateChangeTextView(String  msg);
}
