package com.xuzhouhhy.goandroid;

import android.content.Context;
import android.content.Intent;

import com.xuzhouhhy.goandroid.app.App;
import com.xuzhouhhy.goandroid.camera.CameraActivity;
import com.xuzhouhhy.goandroid.customview.ui.CustomViewActivity;
import com.xuzhouhhy.goandroid.opengl.AirHockeyActivity;
import com.xuzhouhhy.goandroid.opengl.OpenGlActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 入口枚举
 * Created by xuzhouhhy on 2017/8/13.
 */

public enum Extarnce {

    CUSTOM_VIEW(R.string.title_customview),
    IMAGEVIEW_SHOW(R.string.title_imageview),
    OPENGL(R.string.title_opengl),
    CAMERA(R.string.title_custom_camera);

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
            case R.string.title_opengl:
                enterOpenglView(context);
                break;
            case R.string.title_airhockey:
                enterAirhockeyglView(context);
                break;
            case R.string.title_custom_camera:
                enterCustomCamera(context);
                break;
            default:
                break;
        }
    }

    private void enterAirhockeyglView(Context context) {
        Intent intent = new Intent(context, AirHockeyActivity.class);
        context.startActivity(intent);
    }

    private void enterOpenglView(Context context) {
        Intent intent = new Intent(context, OpenGlActivity.class);
        context.startActivity(intent);
    }

    private void enterImageView(Context context) {
        Intent intent = new Intent(context, CustomViewActivity.class);
        context.startActivity(intent);
    }

    private void enterCustomView(Context context) {
        Intent intent = new Intent(context, CustomViewActivity.class);
        context.startActivity(intent);
    }

    private void enterCustomCamera(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
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
