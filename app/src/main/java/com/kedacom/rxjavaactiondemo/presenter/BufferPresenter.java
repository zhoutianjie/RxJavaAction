package com.kedacom.rxjavaactiondemo.presenter;

import android.util.Log;

import com.kedacom.rxjavaactiondemo.base.BasePresenter;
import com.kedacom.rxjavaactiondemo.view.BufferView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by zhoutianjie on 2019/4/16.
 */

public class BufferPresenter extends BasePresenter {


    private CompositeDisposable disposable;
    private volatile boolean cancel = false;
    private Observable<Double> observable;
    private DisposableObserver<List<Double>> mDisposableObserver;

    public BufferPresenter() {
        disposable = new CompositeDisposable();
        observable = Observable.interval(0,TimeUnit.MILLISECONDS)
        .map(this::emitter);
        mDisposableObserver = new DisposableObserver<List<Double>>() {
            @Override
            public void onNext(List<Double> doubles) {
                double result = 0;
                if(doubles.size()>0){
                    for (Double d:doubles){
                        result+=d;
                    }
                    result = result/doubles.size();
                    Log.e(TAG,"更新平却温度："+result);
                    if(null != getView()){
                        ((BufferView)getView()).updateUI(result);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.buffer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDisposableObserver);
        disposable.add(mDisposableObserver);

    }


    @Override
    protected void clear() {
        disposable.clear();
        cancel = true;
    }

    private Double emitter(Long aLong){
        Double temp = Math.random() * 25 + 5;
        Log.e(TAG,""+temp);
        return temp;
    }
}
