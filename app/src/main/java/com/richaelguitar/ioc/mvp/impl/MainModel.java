package com.richaelguitar.ioc.mvp.impl;

import com.richaelguitar.ioc.mvp.IModel;

import java.util.ArrayList;
import java.util.List;

public class MainModel<T> implements IModel {

    private List<T> data = new ArrayList<>();

    public MainModel() {
       this.data.add((T)"https://baidu.com");
    }

    public void loadBanner(ResultCallback<T> resultCallback){
        resultCallback.onCompete(data);
    }

    interface ResultCallback<T>{
        void onCompete(List<T> data);
    }
}
