package com.richaelguitar.ioc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.richaelguitar.ioc.inject.InjectManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.with().inject(this);
        initData();
    }

    public abstract void initData();
}
