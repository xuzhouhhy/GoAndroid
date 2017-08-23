package com.xuzhouhhy.goandroid.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuzhouhhy.goandroid.R;
import com.xuzhouhhy.goandroid.customview.view.CameraSurfaceView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * custom camera
 * Created by xuzhouhhy on 2017/8/21.
 */

public class CameraActivity extends Activity implements View.OnClickListener, SensorEventListener {

    private CameraSurfaceView mSurfaceView;

    private Button mBtnTakePicture;
    private Button mBtnOk;
    private Button mBtnCancel;

    private TextView mTvDirection;

    private FrameLayout mFrameLayout;

    private SensorManager mSensorManager;
    private Sensor mAccSeneor;
    private Sensor mMagSensor;

    private String mDirection;

    private double mAzimuth;

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
        setContentView(R.layout.activity_custom_camera);
        if (!checkCameraHardware(this)) {
            Toast.makeText(this, "设备没有相机功能", Toast.LENGTH_LONG);
        } else {
            mSurfaceView = new CameraSurfaceView(this);
            mBtnTakePicture = (Button) findViewById(R.id.btnTakePicture);
            mBtnTakePicture.setOnClickListener(this);
            mBtnOk = (Button) findViewById(R.id.btnOk);
            mBtnOk.setOnClickListener(this);
            mBtnCancel = (Button) findViewById(R.id.btnCancel);
            mBtnCancel.setOnClickListener(this);
            mTvDirection = (TextView) findViewById(R.id.tvDirection);
            onTakePicture();
            mFrameLayout = (FrameLayout) findViewById(R.id.cameraPreview);
            mFrameLayout.addView(mSurfaceView);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mAccSeneor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mMagSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, mAccSeneor, SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this, mMagSensor, SensorManager.SENSOR_DELAY_GAME);
            mTimer.schedule(mTimerTask, 1000, 2000);
        }
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTakePicture:
                mSurfaceView.onFocus();
                break;
            case R.id.btnOk:
                onOk();
                break;
            case R.id.btnCancel:
                onCancel();
                break;
            default:
                break;
        }
    }

    private void onCancel() {
        onTakePicture();
        mSurfaceView.onCancel();
    }

    private void onOk() {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 拍照时按钮状态
     */
    public void onTakePicture() {
        mBtnTakePicture.setVisibility(View.VISIBLE);
        mBtnOk.setVisibility(View.INVISIBLE);
        mBtnCancel.setVisibility(View.INVISIBLE);
    }

    /**
     * 拍照后按钮状态
     */
    public void onTokenPicture() {
        mBtnTakePicture.setVisibility(View.INVISIBLE);
        mBtnOk.setVisibility(View.VISIBLE);
        mBtnCancel.setVisibility(View.VISIBLE);
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
            mValues[i] = (float) Math.toDegrees(mValues[i]);
        }
        mDirection = calculateDirection(mValues);
    }

    private String calculateDirection(float[] values) {
        if (Math.abs(values[0]) > 180.0 || Math.abs(values[1]) > 90.0 || Math.abs(values[2]) > 180.0) {
            return "方向传感器数据数据超限";
        }
        if (Math.abs(values[1]) < 5.0 && Math.abs(values[2]) < 5.0) {
            return "摄像头朝下，无法判断角度";
        }
        if (Math.abs(values[1]) < 5.0 && Math.abs(Math.abs(values[2]) - 180) < 5.0) {
            return "摄像头朝上，无法判断角度";
        }

        //设备竖屏垂直，沿用内存保留的方向
        if (Math.abs(Math.abs(values[1]) - 90.0) <= 5.0) {
            if (mDirection == null) {
                return "请调整设备";
            }
            return mDirection;
        }
        //通过俯仰角判断竖屏
        if (Math.abs(Math.abs(values[1]) - 90.0) <= 30.0) {
            //设备插孔朝上
            if (values[1] < 0) {
                //设备前倾
                if (Math.abs(values[2]) <= 90) {
                    if (values[0] >= 0.0) {
                        return analyzeValue(values[0]);
                    } else {
                        return analyzeValue(values[0] + 360.0);
                    }
                } else {//设备后倾
                    return analyzeValue(values[0] + 180.0);
                }
            } else {//设备插孔朝下
                //设备前倾
                if (Math.abs(values[2]) <= 90) {
                    return analyzeValue(values[0] + 180.0);
                } else {//设备后倾
                    if (values[0] >= 0.0) {
                        return analyzeValue(values[0]);
                    } else {
                        return analyzeValue(values[0] + 360.0);
                    }
                }
            }
        }
        //横屏，
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
        mAzimuth = v;
        if ((v >= 0.0 && v <= 23.0) || (v > 337.0 && v <= 360.0)) {
            return "北 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 23.0 && v <= 67.0) {
            return "东北 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 67.0 && v <= 112.0) {
            return "东 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 112.0 && v <= 157.0) {
            return "东南 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 157.0 && v <= 202.0) {
            return "南 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 202.0 && v <= 247.0) {
            return "西南 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 247.0 && v <= 292.0) {
            return "西 " + new java.text.DecimalFormat("0").format(v) + "°";
        } else if (v > 292.0 && v <= 337.0) {
            return "西北 " + new java.text.DecimalFormat("0").format(v) + "°";
        }
        return "wtf";
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDirection != null) {
                        mTvDirection.setText(mDirection);
                    } else {
                        mTvDirection.setText("方向位置");
                    }
                }
            });
        }
    };

    private Timer mTimer = new Timer();


}
