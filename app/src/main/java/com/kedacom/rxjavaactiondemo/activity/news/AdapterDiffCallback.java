package com.kedacom.rxjavaactiondemo.activity.news;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.kedacom.rxjavaactiondemo.model.entity.NewsEntity;

import java.util.List;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public class AdapterDiffCallback extends DiffUtil.Callback {

    List<NewsEntity> oldData;
    List<NewsEntity> newData;

    public AdapterDiffCallback(@NonNull List<NewsEntity> oldData, @NonNull List<NewsEntity> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition)._id.equals(newData.get(newItemPosition)._id);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).url.equals(newData.get(newItemPosition).url);
    }
}
