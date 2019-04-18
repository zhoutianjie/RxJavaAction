package com.kedacom.rxjavaactiondemo.activity.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.model.entity.NewsEntity;

import java.util.List;


/**
 * Created by zhoutianjie on 2019/4/16.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private Context mContext;
    private List<NewsEntity> mData;

    public NewsAdapter(Context mContext,@NonNull List<NewsEntity> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setData(@NonNull List<NewsEntity> data){
        mData.clear();
        mData.addAll(data);
    }

    public List<NewsEntity> getData(){
        return mData;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.recycler_item,null);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsEntity entity = mData.get(position);
        if(position%2 == 0){
            holder.Image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
        }else {
            holder.Image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
        }
        Glide.with(mContext)
                .load(entity.url)
                .into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        public ImageView Image;
        public NewsViewHolder(View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.img);
        }
    }
}
