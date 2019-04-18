package com.kedacom.rxjavaactiondemo.view;

import com.kedacom.rxjavaactiondemo.base.BaseView;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public interface NotifyUIView extends BaseView {

    void updateProgress(int percent);
    void complete();
    void downFailed(String e);
}
