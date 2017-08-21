package com.xuzhouhhy.goandroid.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.view.Surface;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

/**
 * Created by xuzhouhhy on 2017/8/21.
 */

public class UtilCamera {

    /**
     * 获取相机示例
     */
    public static Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 设计相机横竖屏
     *
     * @param activity activity
     * @param cameraId 前置？后置？
     * @param camera   camera
     */
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo ci = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, ci);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                ;
                degrees = 270;
                break;
        }
        int result;
        if (ci.facing == CAMERA_FACING_FRONT) {
            result = (ci.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (ci.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     * 获取前置摄像头or后置摄像头
     */
    public static int getCameraId() {
        int mCameraId = CAMERA_FACING_BACK;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, cameraInfo);
        if (cameraInfo.facing == CAMERA_FACING_BACK) {
            mCameraId = CAMERA_FACING_FRONT;
        } else {
            mCameraId = CAMERA_FACING_BACK;
        }
        return mCameraId;
    }

    /**
     * 旋转图片
     *
     * @param bm     bitmap
     * @param degree degree
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
}
