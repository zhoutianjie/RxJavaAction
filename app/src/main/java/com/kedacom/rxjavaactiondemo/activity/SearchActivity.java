package com.kedacom.rxjavaactiondemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.kedacom.rxjavaactiondemo.R;
import com.kedacom.rxjavaactiondemo.base.BaseActivity;
import com.kedacom.rxjavaactiondemo.presenter.SearchPresenter;
import com.kedacom.rxjavaactiondemo.view.SearchView;

/**
 *
 * 几乎每个应用程序都提供了搜索功能，某些应用还提供了搜索联想。
 * 对于一个搜索联想功能，最基本的实现流程为：客户端通过EditText的addTextChangedListener方法监听输入框的变化，
 * 当输入框发生变化之后就会回调afterTextChanged方法，客户端利用当前输入框内的文字向服务器发起请求，
 * 服务器返回与该搜索文字关联的结果给客户端进行展示。
 在该场景下，有几个可以优化的方面：

 在用户连续输入的情况下，可能会发起某些不必要的请求。例如用户输入了abc，那么按照上面的实现，客户端就会发起a、ab、abc三个请求。
 当搜索词为空时，不应该发起请求。
 如果用户依次输入了ab和abc，那么首先会发起关键词为ab请求，
 之后再发起abc的请求，但是abc的请求如果先于ab的请求返回，那么就会造成用户期望搜索的结果为abc，最终展现的结果却是和ab关联的。

 * Created by zhoutianjie on 2019/4/16.
 */

public class SearchActivity extends BaseActivity implements SearchView{


    private SearchPresenter presenter;
    private EditText mSearchEt;
    private TextView mSearchTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        presenter = new SearchPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void initView() {
        mSearchEt = findViewById(R.id.search_et);
        mSearchTv = findViewById(R.id.result_tv);
    }

    @Override
    protected void registerListener() {
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.search(s.toString());
            }
        });
    }

    @Override
    public void getSearchResult(String result) {
        mSearchTv.setText(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
