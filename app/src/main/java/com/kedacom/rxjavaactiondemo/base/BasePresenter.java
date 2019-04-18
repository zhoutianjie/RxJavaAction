package com.kedacom.rxjavaactiondemo.base;

import java.lang.ref.WeakReference;

/**
 * Created by zhoutianjie on 2019/4/16.
 */

public abstract class BasePresenter{
    protected String TAG = this.getClass().getSimpleName();
    private BaseView view;
    private WeakReference<BaseView> mViewRef;

    public void attachView(BaseView pView){
        mViewRef = new WeakReference<BaseView>(pView);
    }

    protected boolean isAttach(){
        return mViewRef!=null && mViewRef.get()!=null;
    }

    public BaseView getView(){
        return isAttach()? mViewRef.get():null;
    }

    public void onDetach(){
        clear();
       if(null!=mViewRef){
           mViewRef.clear();
           mViewRef = null;
       }
    }

    protected abstract void clear();
}
