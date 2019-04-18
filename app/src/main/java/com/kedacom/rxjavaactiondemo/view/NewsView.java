package com.kedacom.rxjavaactiondemo.view;

import com.kedacom.rxjavaactiondemo.base.BaseView;
import com.kedacom.rxjavaactiondemo.model.entity.NewsEntity;

import java.util.List;

/**
 * Created by zhoutianjie on 2019/4/17.
 */

public interface NewsView extends BaseView {

    void getNewsEntitySuccess(List<NewsEntity> entities);
    void getNewsEntityFail(String err);
}
