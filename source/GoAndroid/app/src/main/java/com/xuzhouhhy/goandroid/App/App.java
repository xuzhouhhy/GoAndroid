package com.xuzhouhhy.goandroid.App;

import android.app.Application;

/**
 * app
 * Created by xyzhouhhy on 2017/8/13.
 */

public class App extends Application {

    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
