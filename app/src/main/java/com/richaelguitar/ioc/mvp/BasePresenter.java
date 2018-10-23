package com.richaelguitar.ioc.mvp;


import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IView> {

    private WeakReference<V> weakReference;

    public void onAttachView(V view){
        weakReference = new WeakReference<>(view);
    }

    public void onDetachView(){
        if(isAttached()){
            weakReference.clear();
        }
    }

    private boolean isAttached() {
        return weakReference!=null&&weakReference.get()!=null;
    }

    public  V getTargetView(){
        if(isAttached()){
            return weakReference.get();
        }
        return null;
    }


   public abstract void fetch();
}
