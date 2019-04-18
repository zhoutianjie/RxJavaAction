package com.kedacom.rxjavaactiondemo.presenter;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kedacom.rxjavaactiondemo.base.BasePresenter;

import com.kedacom.rxjavaactiondemo.model.entity.NewsResultApi;
import com.kedacom.rxjavaactiondemo.model.net.NewsApi;
import com.kedacom.rxjavaactiondemo.view.NewsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.observers.DisposableObserver;

import io.reactivex.schedulers.Schedulers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by zhoutianjie on 2019/4/17.
 */

public class NewsPresenter extends BasePresenter {

    private CompositeDisposable disposable;
    private NewsApi newsApi;
    private DisposableObserver<NewsResultApi> observer;


    public NewsPresenter() {
        disposable = new CompositeDisposable();
    }


    public void LoadImage(String category,int count,int page){
        if(newsApi == null){

            newsApi = new Retrofit.Builder()
                    .baseUrl("http://gank.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(NewsApi.class);
        }


            observer = new DisposableObserver<NewsResultApi>() {
                @Override
                public void onNext(NewsResultApi newsEntities) {
                    Log.e(TAG,newsEntities.toString());

                    if(getView()!=null){
                        ((NewsView)getView()).getNewsEntitySuccess(newsEntities.results);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG,e.getMessage());
                    if(getView()!=null){
                        ((NewsView)getView()).getNewsEntityFail(e.getMessage());
                    }
                }

                @Override
                public void onComplete() {
                    Log.e(TAG,"onComplete");
                }
            };


        newsApi.getNews(category,count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);




        disposable.add(observer);
    }

    @Override
    protected void clear() {

    }


}
