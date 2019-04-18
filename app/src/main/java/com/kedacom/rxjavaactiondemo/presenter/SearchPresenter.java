package com.kedacom.rxjavaactiondemo.presenter;

import android.util.Log;

import com.kedacom.rxjavaactiondemo.base.BasePresenter;
import com.kedacom.rxjavaactiondemo.view.SearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public class SearchPresenter extends BasePresenter {

    private PublishSubject<String> mPublishSubject;
    private DisposableObserver<String> mDisposableObserver;
    private CompositeDisposable disposable;


    public SearchPresenter() {
        disposable = new CompositeDisposable();
        mPublishSubject = PublishSubject.create();
        mDisposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                if(null!=getView()){
                    ((SearchView)getView()).getSearchResult(s);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mPublishSubject.debounce(200, TimeUnit.MILLISECONDS)
                .filter(query->query.length()>0)
                .switchMap(query->getObservable(query))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDisposableObserver);
        disposable.add(mDisposableObserver);
    }

    private Observable<String> getObservable(String query){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "开始请求，关键词为：" + query);
                try {
                    Thread.sleep(100 + (long) (Math.random() * 500));
                } catch (InterruptedException e) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                    }
                }
                Log.e(TAG, "结束请求，关键词为：" + query);
                emitter.onNext("完成搜索，关键词为：" + query);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    public void search(String query){
        mPublishSubject.onNext(query);
    }

    @Override
    protected void clear() {
        disposable.clear();
    }
}
