package com.kedacom.rxjavaactiondemo.presenter;

import android.util.Log;

import com.kedacom.rxjavaactiondemo.base.BasePresenter;
import com.kedacom.rxjavaactiondemo.view.NotifyUIView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public class NotifyUIPresent extends BasePresenter {

    private CompositeDisposable disposable;
    private volatile boolean cancel = false;

    public NotifyUIPresent() {
        disposable = new CompositeDisposable();
    }


    public void startDownLoad(){
        Observable<Integer> observable = Observable.create(this::downLoad);
        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                if(null!=getView()){
                    ((NotifyUIView)getView()).updateProgress(integer);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(null!=getView()){
                    ((NotifyUIView)getView()).downFailed(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                if(null!=getView()){
                    ((NotifyUIView)getView()).complete();
                }
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableObserver);

        disposable.add(disposableObserver);
    }

    /**
     * 模拟下载
     */
    private void downLoad(ObservableEmitter emitter){

       for (int i = 0;i<100;i++){
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               if(!emitter.isDisposed()){
                   emitter.onError(e);
               }
           }
           if(cancel){
               break;
           }
           Log.e(TAG,"emitter "+i);
           emitter.onNext(i);
       }

       emitter.onComplete();
    }

    @Override
    protected void clear() {
        disposable.clear();
        cancel = true;
    }
}
