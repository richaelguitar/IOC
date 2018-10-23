package com.richaelguitar.ioc.mvp.impl;

import com.richaelguitar.ioc.mvp.BasePresenter;

import java.util.List;

public class MainPresenter extends BasePresenter {

    private MainModel<String> mainModel = new MainModel<>();

    @Override
    public void fetch() {
        mainModel.loadBanner(new MainModel.ResultCallback<String>() {
            @Override
            public void onCompete(List<String> data) {
                ((MainView)getTargetView()).loadBannerList(data);
            }
        });
    }
}
