package com.richaelguitar.ioc;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.richaelguitar.ioc.annotation.BindView;
import com.richaelguitar.ioc.annotation.ContentView;
import com.richaelguitar.ioc.annotation.OnClick;
import com.richaelguitar.ioc.annotation.OnLongClick;
import com.richaelguitar.ioc.mvp.BasePresenter;
import com.richaelguitar.ioc.mvp.impl.MainPresenter;
import com.richaelguitar.ioc.mvp.impl.MainView;

import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.btn_click)
    private Button button;

    @BindView(R.id.tv_show)
    private TextView textView;

    private MainPresenter mainPresenter = new MainPresenter();

    @Override
    public void initData() {
        mainPresenter.fetch();
        Toast.makeText(this,textView.getText().toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public BasePresenter buildPresenter() {
        return mainPresenter;
    }


    @OnClick({R.id.btn_click,R.id.btn_long_click})
    public void  show(View view){
        Toast.makeText(this,"点击了按钮",Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.btn_long_click)
    public boolean showLong(View view){
        Toast.makeText(this,"长按了按钮",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void loadBannerList(List<String> data) {
        for (String url:data){
            Toast.makeText(MainActivity.this,"url="+url,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fetch() {

    }
}
