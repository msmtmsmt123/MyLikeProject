package com.zj.www.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by zj on 2017/8/11.
 * 实现懒加载功能
 */
public abstract class LazyLoadFragment<T extends IBasePresenter> extends BaseFragment<T> {

    protected boolean isViewInitiated; // 是否加载布局

    protected boolean isVisibleToUser; // 是否对用户可见

    protected boolean isDataInitiated; // 是否加载数据

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData(); // 准备获取数据
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean flag) {

        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || flag)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    public abstract void fetchData(); // 加载数据

}
