package com.xuzhouhhy.goandroid.direction;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.xuzhouhhy.goandroid.R;

/**
 * 手机朝向
 * Created by xuzhouhhy on 2017/8/22.
 */

public class DirectionActivity extends Activity implements SensorEventListener {

    private TextView mTvDirection;

    private SensorManager mSensorManager;
    private Sensor mAccSeneor;
    private Sensor mMagSensor;

    //加速度传感器数据
    private float[] mAccValue = new float[3];
    //地磁传感器数据
    private float[] mMagValue = new float[3];
    //旋转矩阵，用来保存磁场合加速度数据
    private float[] mR = new float[9];
    //模拟方向传感器数据（原始数据维弧度）
    private float[] mValues = new float[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        mTvDirection = (TextView) findViewById(R.id.tvDirection);
        mTvDirection.setText("方向为你治");
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccSeneor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mAccSeneor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccValue = event.values.clone();//这里是对象，需要克隆一份，否则共用一份数据
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagValue = event.values.clone();//这里是对象，需要克隆一份，否则共用一份数据
        }
        /**public static boolean getRotationMatrix (float[] R, float[] I, float[] gravity, float[] geomagnetic)
         * 填充旋转数组r
         * r：要填充的旋转数组
         * I:将磁场数据转换进实际的重力坐标中 一般默认情况下可以设置为null
         * gravity:加速度传感器数据
         * geomagnetic：地磁传感器数据
         */
        SensorManager.getRotationMatrix(mR, null, mAccValue, mMagValue);
        /**
         * public static float[] getOrientation (float[] R, float[] values)
         * R：旋转数组
         * values ：模拟方向传感器的数据
         */
        SensorManager.getOrientation(mR, mValues);
        //将弧度转化为角度后输出
        StringBuffer buff = new StringBuffer();
        for (float value : mValues) {
            value = (float) Math.toDegrees(value);
            buff.append(value + "  ");
        }
        mTvDirection.setText(buff.toString());
    }
}
