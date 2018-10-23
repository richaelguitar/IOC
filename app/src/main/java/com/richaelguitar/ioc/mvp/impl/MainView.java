package com.richaelguitar.ioc.mvp.impl;

import com.richaelguitar.ioc.mvp.IView;

import java.util.List;

public interface MainView extends IView {

     void loadBannerList(List<String> data) ;
}
