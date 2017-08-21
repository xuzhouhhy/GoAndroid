package com.xuzhouhhy.goandroid.customview.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * 相机预览
 * Created by xuzhouhhy on 2017/8/21.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;

    private Camera mCamera;

    public CameraSurfaceView(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            setParameter();
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //释放相机资源
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(mSurfaceHolder.getSurface()==null){
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
}
