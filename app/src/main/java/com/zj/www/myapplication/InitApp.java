package com.zj.www.myapplication;

import android.app.Application;
import android.content.Context;

import com.zj.www.myapplication.util.SettingUtil;

/**
 * Created by zj on 2017/8/11.
 */
public class InitApp extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        initTheme(); //加载主题

        if (BuildConfig.DEBUG) {

        }
    }

    private void initTheme() {
        SettingUtil settingUtil = SettingUtil.getInstance();
        /*获取是否开启“自动切换夜间模式”*/


    }
}
