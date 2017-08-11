package com.zj.www.myapplication.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by zj on 2017/8/11.
 */
public interface IBaseView<T> {

    /*加载动画*/
    void onShowLoading();

    void onHideLoading();

    void onShowNetError();

    void setPresenter(T presenter); // 设置相应的presenter

    /*绑定生命周期*/
    <T> LifecycleTransformer<T> bindToLife();
}
