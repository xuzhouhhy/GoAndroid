package com.xuzhouhhy.goandroid.customview.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.xuzhouhhy.goandroid.camera.CameraActivity;
import com.xuzhouhhy.goandroid.util.UtilCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.xuzhouhhy.goandroid.util.UtilCamera.getCameraInstance;

/**
 * 相机预览
 * Created by xuzhouhhy on 2017/8/21.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback,
        android.hardware.Camera.PictureCallback, Camera.AutoFocusCallback {

    static String pn;

    private SurfaceHolder mSurfaceHolder;

    private Camera mCamera;

    private Activity mActivity;

    public CameraSurfaceView(Activity activity) {
        super(activity.getBaseContext());
        mActivity = activity;
        mCamera = getCameraInstance();
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPreview() throws IOException {
        SurfaceHolder holder = getHolder();
        if (mCamera == null) {
            mCamera = getCameraInstance();
        }
        mCamera.setPreviewDisplay(holder);
        setParameter();
        UtilCamera.setCameraDisplayOrientation(mActivity, UtilCamera.getCameraId(), mCamera);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置照片格式
     */
    private void setParameter() {
        Camera.Parameters parameters = mCamera.getParameters(); // 获取各项参数
        parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
        parameters.setJpegQuality(100); // 设置照片质量
        //获得相机支持的照片尺寸,选择合适的尺寸
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        int maxSize = Math.max(this.getWidth(), this.getHeight());
        int length = sizes.size();
        if (maxSize > 0) {
            for (int i = 0; i < length; i++) {
                if (maxSize <= Math.max(sizes.get(i).width, sizes.get(i).height)) {
                    parameters.setPictureSize(sizes.get(i).width, sizes.get(i).height);
                    break;
                }
            }
        }
        List<Camera.Size> ShowSizes = parameters.getSupportedPreviewSizes();
        int showLength = ShowSizes.size();
        if (maxSize > 0) {
            for (int i = 0; i < showLength; i++) {
                if (maxSize <= Math.max(ShowSizes.get(i).width, ShowSizes.get(i).height)) {
                    parameters.setPreviewSize(ShowSizes.get(i).width, ShowSizes.get(i).height);
                    break;
                }
            }
        }
        mCamera.setParameters(parameters);
    }

    public void releaseCamera() {
        //释放相机资源
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            mCamera.takePicture(null, null, this);
            ((CameraActivity) mActivity).onTokenPicture();
        }
    }


    @Override
    public void onPictureTaken(final byte[] data, android.hardware.Camera camera) {
        File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (picture == null) {
            Toast.makeText(mActivity, "返回照片为空", Toast.LENGTH_LONG);
            return;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        pn = picture.getPath() + File.separator + sf.format(new Date()) + ".jpg";
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(pn);
                try {
                    // 获取当前旋转角度, 并旋转图片
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    bitmap = UtilCamera.rotateBitmapByDegree(bitmap, 180);
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.close();
                    bitmap.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 自动对焦
     */
    public void onFocus() {
        mCamera.autoFocus(this);
    }

    /**
     * 拍照不满意
     */
    public void onCancel() {
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File tempFile = new File(pn);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }
}
