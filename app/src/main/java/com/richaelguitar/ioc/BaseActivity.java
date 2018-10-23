package com.richaelguitar.ioc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.richaelguitar.ioc.inject.InjectManager;
import com.richaelguitar.ioc.mvp.BasePresenter;
import com.richaelguitar.ioc.mvp.IView;

public abstract class BaseActivity<V extends IView,P extends BasePresenter<V>> extends AppCompatActivity {

    private P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.with().inject(this);
        presenter = buildPresenter();
        presenter.onAttachView((V)this);
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.onDetachView();
        }
    }

    public abstract void initData();

    public abstract P buildPresenter();
}
