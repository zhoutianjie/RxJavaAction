package com.kedacom.rxjavaactiondemo.model.entity;

import java.util.List;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public class NewsEntity {
    public String _id;
    public String url;

    @Override
    public String toString() {
        return "NewsEntity{" +
                "_id='" + _id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


}
