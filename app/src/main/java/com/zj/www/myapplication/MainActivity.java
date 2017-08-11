package com.zj.www.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;

import com.zj.www.myapplication.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;


    // 功能：将屏幕向下滑动，nav和toolbar不显示

    // 显示布局文件
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }


}
