package com.zj.www.myapplication.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.color.CircleView;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zj.www.myapplication.Constant;
import com.zj.www.myapplication.R;
import com.zj.www.myapplication.util.SettingUtil;

/**
 * Created by zj on 2017/8/11.
 */
public class BaseActivity extends RxAppCompatActivity {

    public static final String TAG      = "BaseActivity";
    private             int    iconType = -1;
    private SlidrInterface slidrInterface;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.iconType = SettingUtil.getInstance().getCustomIconValue();
        initSlidable();
    }

    /*侧滑使用，不是用在取消activity活动的使用，现估计是用于viewpage这类的滑动*/
    private void initSlidable() {
        int isSlidable = SettingUtil.getInstance().getSlidable();  // 作用用于左右滑动退出功能

        if (isSlidable != Constant.SLIDABLE_DISABLE) {
            SlidrConfig config = new SlidrConfig.Builder()
                    .edge(isSlidable == Constant.SLIDABLE_EDGE)
                    .build();
            slidrInterface = Slidr.attach(this, config);
        }
    }

    /*初始化toolbar*/
    protected void initToolbar(Toolbar toolbar, Boolean homeAsUpEnable, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnable);
    }


    // toolbar上色
    @Override
    protected void onResume() {
        super.onResume();
        int color = SettingUtil.getInstance().getColor();
        int drawable = Constant.ICONS_DRAWABLES[SettingUtil.getInstance().getCustomIconValue()];
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
            /*最近任务栏上色*/
            ActivityManager.TaskDescription tDesc = new ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    BitmapFactory.decodeResource(getResources(), drawable),
                    color);
            setTaskDescription(tDesc);

            if (SettingUtil.getInstance().getNavBar()) {
                getWindow().setNavigationBarColor(CircleView.shiftColorDown(color));
            } else {
                getWindow().setNavigationBarColor(Color.BLACK);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /*fragment逐个出栈*/
        int count = getSupportFragmentManager().getBackStackEntryCount(); // 栈里fragnemt的个数
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }


    /*根据这里的选择不一样，显示不同的图表*/
    @Override
    protected void onStop() {
        if (iconType != SettingUtil.getInstance().getCustomIconValue()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //更换相应的图标
                    String act = ".MainActivity-";

                    for (String s : Constant.ICONS_TYPE) {

                        getPackageManager().setComponentEnabledSetting(new ComponentName(BaseActivity.this, getPackageName() + act + s),
                                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                PackageManager.DONT_KILL_APP);
                    }

                    act += Constant.ICONS_TYPE[SettingUtil.getInstance().getCustomIconValue()];

                    getPackageManager().setComponentEnabledSetting(new ComponentName(BaseActivity.this, getPackageName() + act),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                }
            }).start();

        }

        super.onStop();
    }
}
