package com.ttp.apt_project;

import android.app.Application;

import com.ttp.http.utils.HttpConfig;

/**
 * @author faqi.tao
 * @time 2019/12/27
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpConfig.baseUrl = "https://wanandroid.com/";
    }
}
