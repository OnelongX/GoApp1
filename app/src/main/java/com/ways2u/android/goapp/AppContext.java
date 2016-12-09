package com.ways2u.android.goapp;

import android.app.Application;

import com.socks.library.KLog;
import com.ways2u.android.net.util.NetContext;

/**
 * Created by huanglong on 2016/12/5.
 */
//配置管理，用户信息管理，数据库管理，网络管理，缓存管理，日志管理
//等等....
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        NetContext.init(this);
        KLog.init(BuildConfig.LOG_DEBUG, "OL");
    }
}
