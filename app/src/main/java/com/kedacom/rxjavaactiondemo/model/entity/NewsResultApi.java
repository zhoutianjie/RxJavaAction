package com.kedacom.rxjavaactiondemo.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoutianjie on 2019/4/17.
 */

public class NewsResultApi {

    public boolean error;
    public List<NewsEntity> results;

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[NewsResultApi]");
        builder.append(error);
        builder.append("\n");
        builder.append("result :");
        builder.append("\n");
        if(results !=null && results.size()>0){
            for (NewsEntity entity:results){
                builder.append(entity.toString());
                builder.append("\n");
            }
        }

        return new String(builder);
    }


}
