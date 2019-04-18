package com.kedacom.rxjavaactiondemo.activity.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.base.BaseActivity;
import com.kedacom.rxjavaactiondemo.model.entity.NewsEntity;
import com.kedacom.rxjavaactiondemo.presenter.NewsPresenter;
import com.kedacom.rxjavaactiondemo.view.NewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * retrofit 使用示例
 * Created by zhoutianjie on 2019/4/16.
 */

public class NewsActivity extends BaseActivity implements NewsView {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsEntity> mData;
    private DiffUtil.DiffResult diffResult;
    private NewsPresenter presenter;
    private int page = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recylerview);
        refreshLayout = findViewById(R.id.refresh);

        mData = new ArrayList<>();
        adapter = new NewsAdapter(this,mData);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void registerListener() {

        refreshLayout.setOnRefreshListener(()->{
            if(!refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(true);
            }
            presenter.LoadImage("福利",9,page++);
        });
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        presenter = new NewsPresenter();
        presenter.attachView(this);

    }

    @Override
    public void getNewsEntitySuccess(List<NewsEntity> entities) {
        if(entities == null || entities.isEmpty()){
            return;
        }

        List<NewsEntity> oldData = adapter.getData();
        diffResult = DiffUtil.calculateDiff(new AdapterDiffCallback(oldData,entities));
        adapter.setData(entities);
        diffResult.dispatchUpdatesTo(adapter);

        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void getNewsEntityFail(String err) {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
