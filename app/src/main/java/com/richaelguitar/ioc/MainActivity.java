package com.richaelguitar.ioc;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.richaelguitar.ioc.annotation.BindView;
import com.richaelguitar.ioc.annotation.ContentView;
import com.richaelguitar.ioc.annotation.OnClick;
import com.richaelguitar.ioc.annotation.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.btn_click)
    private Button button;

    @BindView(R.id.tv_show)
    private TextView textView;

    @Override
    public void initData() {
        Toast.makeText(this,textView.getText().toString(),Toast.LENGTH_SHORT).show();

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
}
