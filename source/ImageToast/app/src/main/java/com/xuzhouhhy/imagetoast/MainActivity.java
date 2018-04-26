package com.xuzhouhhy.imagetoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animation animation = AnimationUtils.loadAnimation(this,R.animator.animation_rotate);
        findViewById(R.id.loading).startAnimation(animation);
    }

    public void onSuccess(View view){
        ToastUtil.showSuccess(this);
    }

    public void onLoading(View view){
        ToastUtil.showLoading(this);
    }

    public void onFail(View view){
        ToastUtil.showFail(this);

    }

    public void onWarning(View view){

        ToastUtil.showWarning(this);

    }

    public void onFinish(View view){

        ToastUtil.showFinish(this);

    }


}
