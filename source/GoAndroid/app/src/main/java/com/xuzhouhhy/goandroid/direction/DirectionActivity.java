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

    private TextView mTvAngle;
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
        mTvAngle = (TextView) findViewById(R.id.tvSensorAngle);
        mTvAngle.setText("角度");
        mTvDirection = (TextView) findViewById(R.id.tvDirection);
        mTvDirection.setText("方向");
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
        /* public static boolean getRotationMatrix (float[] R, float[] I, float[] gravity, float[] geomagnetic)
         * 填充旋转数组r
         * r：要填充的旋转数组
         * I:将磁场数据转换进实际的重力坐标中 一般默认情况下可以设置为null
         * gravity:加速度传感器数据
         * geomagnetic：地磁传感器数据
         */
        SensorManager.getRotationMatrix(mR, null, mAccValue, mMagValue);
        /*
         * public static float[] getOrientation (float[] R, float[] values)
         * R：旋转数组
         * values ：模拟方向传感器的数据
         */
        SensorManager.getOrientation(mR, mValues);
        //将弧度转化为角度后输出
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < mValues.length; i++) {
            if (i == 0) {
                mValues[i] = (float) Math.toDegrees(mValues[i]);
                String s = new java.text.DecimalFormat("0").format(mValues[i]);
                buff.append("方向角：" + s + "  \r\n");
            } else if (i == 1) {
                mValues[i] = (float) Math.toDegrees(mValues[i]);
                String s = new java.text.DecimalFormat("0").format(mValues[i]);
                buff.append("俯仰角：" + s + "  \r\n");
            } else if (i == 2) {
                mValues[i] = (float) Math.toDegrees(mValues[i]);
                String s = new java.text.DecimalFormat("0").format(mValues[i]);
                buff.append("旋转角：" + s + "  \r\n");
            }
        }
        mTvAngle.setText(buff.toString());
        mTvDirection.setText(calculateDirection(mValues));
    }

    private String calculateDirection(float[] values) {
        if (values[0] > 180.0 || values[0] < -180.0 || values[1] > 90.0 || values[1] < -90.0 || values[2] > 180.0 || values[2] < -180.0) {
            return "方向传感器数据数据超限";
        }
//        if (-5.0 < values[2] && values[2] < 5.0) {
//            return "摄像头朝下，无法判断角度";
//        }
//        if (-175.0 > values[2] || values[2] > 175.0) {
//            return "摄像头朝上，无法判断角度";
//        }
        if (values[2] < 0) {
            if (values[0] >= -90.0) {
                return analyzeValue(values[0] + 90.0);
            } else {
                return analyzeValue(values[0] + 450.0);
            }
        } else {
            if (values[0] >= 90.0) {
                return analyzeValue(values[0] - 90.0);
            } else {
                return analyzeValue(values[0] + 270.0);
            }
        }
    }

    private String analyzeValue(double v) {
        if ((v >= 0.0 && v <= 23.0) || (v > 337.0 && v <= 360.0)) {
            return "北 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 23.0 && v <= 67.0) {
            return "东北 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 67.0 && v <= 112.0) {
            return "东 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 112.0 && v <= 157.0) {
            return "东南 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 157.0 && v <= 202.0) {
            return "南 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 202.0 && v <= 247.0) {
            return "西南 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 247.0 && v <= 292.0) {
            return "西 " + new java.text.DecimalFormat("0").format(v);
        } else if (v > 292.0 && v <= 337.0) {
            return "西北 " + new java.text.DecimalFormat("0").format(v);
        }
        return "wtf";
    }

}
