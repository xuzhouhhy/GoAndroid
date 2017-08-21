package com.xuzhouhhy.goandroid.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xuzhouhhy.goandroid.R;
import com.xuzhouhhy.goandroid.customview.view.CameraSurfaceView;
import com.xuzhouhhy.goandroid.util.UtilCamera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.xuzhouhhy.goandroid.util.UtilCamera.getCameraInstance;

/**
 * custom camera
 * Created by xuzhouhhy on 2017/8/21.
 */

public class CameraActivity extends Activity implements android.hardware.Camera.PictureCallback,
        View.OnClickListener, Camera.AutoFocusCallback {

    private CameraSurfaceView mSurfaceView;

    private Camera mCamera;

    private Button mBtnTakePicture;

    private FrameLayout mFrameLayout;

    private int mCameraId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        if (!checkCameraHardware(this)) {
            Toast.makeText(this, "设备没有相机功能", Toast.LENGTH_LONG);
        } else {
            mCamera = getCameraInstance();
            mCameraId = UtilCamera.getCameraId();
            mSurfaceView = new CameraSurfaceView(this, mCamera);
            mBtnTakePicture = (Button) findViewById(R.id.btnTakePicture);
            mBtnTakePicture.setOnClickListener(this);
            mFrameLayout = (FrameLayout) findViewById(R.id.cameraPreview);
            mFrameLayout.addView(mSurfaceView);
            UtilCamera.setCameraDisplayOrientation(this, mCameraId, mCamera);
        }
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onPictureTaken(final byte[] data, android.hardware.Camera camera) {
        File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (picture == null) {
            Toast.makeText(this, "返回照片为空", Toast.LENGTH_LONG);
            return;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        final String pn = sf.format(new Date()) + ".jpg";
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(pn);
                try {
                    // 获取当前旋转角度, 并旋转图片
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    bitmap = UtilCamera.rotateBitmapByDegree(bitmap, 90);
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
                    bitmap.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        mCamera.autoFocus(this);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            mCamera.takePicture(null, null, this);
        }
    }
}
