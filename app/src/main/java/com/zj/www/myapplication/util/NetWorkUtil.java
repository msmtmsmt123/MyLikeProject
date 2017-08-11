package com.zj.www.myapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zj on 2017/8/11.
 */
public class NetWorkUtil {

    /*网络管理工具*/
    /*判断是否有网络连接*/
    public static boolean isNetWorkConnected(Context context) {

        if (null != context) {
            // 获取连接管理类
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo info = manager.getActiveNetworkInfo();

            return null != info && info.isAvailable();
        }
        return false;
    }

    /*判断是否有wifi连接*/

    public static boolean isWifiConnected(Context context) {

        if (null != context) {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    //判断是否是mobile网络
    public static boolean isMobileConnected(Context context) {

        if (null != context) {

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
