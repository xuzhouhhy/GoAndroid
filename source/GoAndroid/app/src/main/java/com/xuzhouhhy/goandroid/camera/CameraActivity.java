package com.xuzhouhhy.goandroid.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xuzhouhhy.goandroid.R;
import com.xuzhouhhy.goandroid.customview.view.CameraSurfaceView;

/**
 * custom camera
 * Created by xuzhouhhy on 2017/8/21.
 */

public class CameraActivity extends Activity implements View.OnClickListener {

    private CameraSurfaceView mSurfaceView;

    private Button mBtnTakePicture;
    private Button mBtnOk;
    private Button mBtnCancel;

    private FrameLayout mFrameLayout;

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
            onTakePicture();
            mFrameLayout = (FrameLayout) findViewById(R.id.cameraPreview);
            mFrameLayout.addView(mSurfaceView);
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

}
