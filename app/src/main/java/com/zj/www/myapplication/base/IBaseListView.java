package com.zj.www.myapplication.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by zj on 2017/8/11.
 */
public interface IBaseListView<T> extends IBaseView<T> {

    void onShowLoading();

    void onHideLoading();

    void onShowNetError();

    void setPresenter(T presenter);

    <T> LifecycleTransformer<T> bindToLife();

    void onSetAdapter(List<?> list);

    void onShowNoMore();
}
