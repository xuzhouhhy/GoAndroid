package com.xuzhouhhy.goandroid;

import android.content.Context;
import android.content.Intent;

import com.xuzhouhhy.goandroid.App.App;
import com.xuzhouhhy.goandroid.customview.CustomViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 入口枚举
 * Created by xuzhouhhy on 2017/8/13.
 */

public enum Extarnce {

    CUSTOM_VIEW(R.string.title_customview),
    IMAGEVIEW_SHOW(R.string.title_imageview);

    private int mValue;

    Extarnce(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public void enter(Context context) {
        switch (mValue) {
            case R.string.title_customview:
                enterCustomView(context);
                break;
            case R.string.title_imageview:
                enterImageView(context);
                break;
            default:
                break;
        }
    }

    private void enterImageView(Context context) {
        Intent intent = new Intent(context, CustomViewActivity.class);
        context.startActivity(intent);
    }

    private void enterCustomView(Context context) {
        Intent intent = new Intent(context, CustomViewActivity.class);
        context.startActivity(intent);
    }

    public static List<String> getStrings() {
        List<String> titles = new ArrayList<>();
        Extarnce[] extarnces = values();
        for (Extarnce extarnce : extarnces) {
            titles.add(App.getInstance().getResources().getString(extarnce.getValue()));
        }
        return titles;
    }
}
