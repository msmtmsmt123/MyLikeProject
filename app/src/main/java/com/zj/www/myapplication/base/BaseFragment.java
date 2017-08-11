package com.zj.www.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.RxFragment;

/**
 * Created by zj on 2017/8/11.
 */
public abstract class BaseFragment<T extends IBasePresenter> extends RxFragment implements IBaseView<T> {

    protected T presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayout(), container, false);
        initView(view);
        initData();
        return view;
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolbar(toolbar, homeAsUpEnabled, title);
    }

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int attachLayout();


    /*绑定生命周期*/
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }

}
