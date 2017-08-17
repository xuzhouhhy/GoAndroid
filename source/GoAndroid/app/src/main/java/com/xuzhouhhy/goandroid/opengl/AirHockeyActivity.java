package com.xuzhouhhy.goandroid.opengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

/**
 * opengl
 * Created by xuzhouhhy on 2017/8/16.
 */

public class AirHockeyActivity extends Activity {

    private GLSurfaceView mGLSurfaceView;

    private boolean mRendererSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        boolean supportEs2 = ci.reqGlEsVersion >= 0x20000;
        if(supportEs2){
            //Request an OpenGl es 2.0 compatible context
            mGLSurfaceView.setEGLContextClientVersion(2);

            mGLSurfaceView.setRenderer(new FirstOpenGLProjectrender());
            mRendererSet = true;
        }
        setContentView(mGLSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRendererSet){
            mGLSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mRendererSet){
            mGLSurfaceView.onResume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
