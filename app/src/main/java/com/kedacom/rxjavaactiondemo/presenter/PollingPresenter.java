package com.kedacom.rxjavaactiondemo.presenter;

import android.util.Log;

import com.kedacom.rxjavaactiondemo.base.BasePresenter;
import com.kedacom.rxjavaactiondemo.view.PollingView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * repeatWhen 操作符
 * Created by zhoutianjie on 2019/4/17.
 */

public class PollingPresenter extends BasePresenter {

    private CompositeDisposable disposable;
    private Observable<Long> fixObservable;
    private Observable<Long> changeObservable;

    public PollingPresenter() {
        disposable = new CompositeDisposable();
        fixObservable = Observable.intervalRange(0,5,0,3000, TimeUnit.MILLISECONDS);
        changeObservable = Observable.just(2L);

    }

    public void startfixPolling(){
        DisposableObserver disposableObserver = getfixObserver();
        fixObservable
                .doOnNext(aLong -> dowork())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        disposable.add(disposableObserver);
    }


    public void startChangePolling(){
        DisposableObserver<Long> disposableObserver = getChangeObserver();
        changeObservable
                .doOnComplete(this::dowork)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {

                    private long mRepeatCount;
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                if (++mRepeatCount > 4) {
                                    //return Observable.empty(); //发送onComplete消息，无法触发下游的onComplete回调。
                                    return Observable.error(new Throwable("Polling work finished")); //发送onError消息，可以触发下游的onError回调。
                                }
                                Log.d(TAG, "startAdvancePolling apply");
                                return Observable.timer(3000 + mRepeatCount * 1000, TimeUnit.MILLISECONDS);
                            }
                        });



                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        disposable.add(disposableObserver);
    }

    private DisposableObserver<Long> getfixObserver(){
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                Log.e(TAG,"发送数据："+aLong);
                if(getView()!=null){
                    ((PollingView)getView()).updatefixTextView(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"发送数据："+e);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<Long> getChangeObserver(){
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                Log.e(TAG,"发送数据："+aLong);
                if(getView()!=null){
                    ((PollingView)getView()).updateChangeTextView(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Throwable："+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"Throwable："+"complete");
            }
        };
    }

    private void dowork(){
        long workTime = 2000L; //1秒上下的工作间隔 上游是3秒的发送间隔,如果工作时长超过了上游的时间间隔，这里假设工作间隔是6s，那么
                                //下游每次也是间隔6秒获得消息
        try {
            Log.e(TAG, "doWork start,  threadId=" + Thread.currentThread().getId());
            Thread.sleep(workTime);
            Log.e(TAG, "doWork finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void clear() {
        disposable.clear();
    }
}
