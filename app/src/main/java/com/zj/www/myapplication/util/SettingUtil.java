package com.zj.www.myapplication.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.zj.www.myapplication.InitApp;
import com.zj.www.myapplication.R;

/**
 * Created by zj on 2017/8/11.
 */
public class SettingUtil {
    // 作用：用于判断是否使用无图模式切换功能
    private SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(InitApp.appContext);

    // 作用使用final修饰
    private static final class SettingUtilInstance {
        private static final SettingUtil instance = new SettingUtil();
    }

    public static SettingUtil getInstance() {
        return SettingUtilInstance.instance;
    }

    /*获取是否开启无图模式*/
    public boolean getIsNoPhotoMode() {
        return setting.getBoolean("switch_noPhotoMode", false) && NetWorkUtil.isNetWorkConnected(InitApp.appContext); // 并判断是否有网
    }

    /*获得主题颜色*/

    public int getColor() {

        int defaultColor = InitApp.appContext.getResources().getColor(R.color.colorPrimary);

        int color = setting.getInt("color", defaultColor);

        if ((color != 0) && Color.alpha(color) != 255) { // 如果不是白色或黑色的话，就是使用默认的颜色
            return defaultColor;
        }
        return color;
    }

    /*设置主题颜色*/

    public void setColor(int color) {
        setting.edit().putInt("color", color).apply();
    }

    /*获取是否开启黑夜模式*/
    public boolean getIsNightMode() {
        return setting.getBoolean("switch_nightMode", false);
    }

    // 设置夜间模式
    public void setIsNightMode(boolean flag) {
        setting.edit().putBoolean("switch_nightMode", flag).apply();
    }

    /*获取是否开启自动切换夜间模式*/
    public boolean getIsAutoNightMode() {
        return setting.getBoolean("auto_nightMode", false);
    }

    /*获取是否开启自动切换夜间模式*/
    public void setIsAtuoNightMode(boolean flag) {
        setting.edit().putBoolean("auto_nightMode", flag).apply();
    }

    public String getNightStarHour() {

        return setting.getString("night_startHour", "22");
    }

    public void setNightStartHour(String nightStartHour) {
        setting.edit().putString("night_statrHour", nightStartHour).apply();
    }

    public String getNightStartMinute() {
        return setting.getString("night_startMinute", "00");
    }

    public void setNightStartMinute(String nightStartMinute) {
        setting.edit().putString("night_startMinute", nightStartMinute).apply();
    }

    public String getDayStartHour() {
        return setting.getString("day_startHour", "06");
    }

    public void setDayStartHour(String dayStartHour) {

        setting.edit().putString("day_startHour", dayStartHour).apply();
    }

    public String getDayStartMinute() {

        return setting.getString("day_startMinute", "00");
    }

    public void setDayStartMinute(String dayStartMinute) {

        setting.edit().putString("day_startMinute", dayStartMinute).apply();
    }

    /*获取是否开启导航栏上色*/
    public boolean getNavBar() {
        return setting.getBoolean("nav_bar", false);
    }

    /*获取是否视频强制横屏*/
    public boolean getIsVideoForceLandscape() {

        return setting.getBoolean("video_force_landscape", false);
    }

    /*获取图标值*/
    public int getCustomIconValue() {
        String cusotmIcon = setting.getString("cusotm_icon", "0");
        return Integer.parseInt(cusotmIcon);
    }

    /*获取滑动返回值*/
    public int getSlidable() {
        String s = setting.getString("slidable", "1");
        return Integer.parseInt(s);
    }

    /*获取是否开启视频自动播放*/
    public boolean getIsVideoAutoPlay() {

        return setting.getBoolean("video_auto_play", false) && NetWorkUtil.isNetWorkConnected(InitApp.appContext);
    }
}
