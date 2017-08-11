package com.zj.www.myapplication.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zj.www.myapplication.R;
import com.zj.www.myapplication.bean.LoadingEndBean;
import com.zj.www.myapplication.util.SettingUtil;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zj on 2017/8/11.
 */
public abstract class BaseListFragment<T extends IBasePresenter> extends LazyLoadFragment<T> implements IBaseListView<T>, SwipeRefreshLayout.OnRefreshListener{
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshiLayout;
    protected MultiTypeAdapter adapter;
    protected boolean canLoadMore = false;
    protected Items oldItems = new Items();

    // 对于显现list类型的布局

    @Override
    protected int attachLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        swipeRefreshiLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        swipeRefreshiLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        swipeRefreshiLayout.setOnRefreshListener(this);
    }


    @Override
    public void onShowLoading() {
        swipeRefreshiLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshiLayout.setRefreshing(true);
            }
        });
    }


    @Override
    public void onHideLoading() {
        swipeRefreshiLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshiLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onShowNetError() {  //无网情况下直接显示空白内容
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setItems(new Items());
                adapter.notifyDataSetChanged();
                canLoadMore = false;
            }
        });

    }


    @Override
    public void onShowNoMore() {  //加载到全部的内容了
     getActivity().runOnUiThread(new Runnable() {
         @Override
         public void run() {
             if (oldItems.size() > 0) {
                 Items newItems = new Items(oldItems);
                 newItems.remove(newItems.size() - 1);
                 newItems.add(new LoadingEndBean());
                 adapter.setItems(newItems);
                 adapter.notifyDataSetChanged();
             } else if (oldItems.size() == 0) {
                 oldItems.add(new LoadingEndBean());
                 adapter.setItems(oldItems);
                 adapter.notifyDataSetChanged();
             }
             canLoadMore = false;
         }
     });
    }


    @Override
    public void onRefresh() {
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == 0) {
            presenter.doRefresh(); //刷新
        }
        recyclerView.scrollToPosition(5);
        recyclerView.smoothScrollToPosition(0);

    }
}
