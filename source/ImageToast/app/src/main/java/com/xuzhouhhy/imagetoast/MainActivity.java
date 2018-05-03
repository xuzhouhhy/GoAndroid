package com.xuzhouhhy.imagetoast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animation animation = AnimationUtils.loadAnimation(this, R.animator.animation_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        findViewById(R.id.loading).startAnimation(animation);
    }

    public void onSuccess(View view) {
        ToastUtil.showWithSuccessIcon(this, R.string.test_success);
    }

    public void onLoading(View view) {
        ToastUtil.showWithLoadingIcon(this);
    }

    public void onFail(View view) {
        ToastUtil.showWithFailIcon(this, R.string.test_fail);

    }

    public void onWarning(View view) {

        ToastUtil.showWithWarningIcon(this, R.string.test_warn);

    }

    public void onFinish(View view) {

        ToastUtil.showMsg(this, R.string.message_test);
    }


}
