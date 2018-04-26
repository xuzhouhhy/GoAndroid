package com.xuzhouhhy.imagetoast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LoadingView extends View {

    private Paint mPaint;
    private Paint mInnerPaint;
    private Paint mMiddlePaint;

    public LoadingView(Context context) {
        super(context);
        initPaint();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.white));
        mInnerPaint = new Paint();
        mInnerPaint.setColor(getResources().getColor(R.color.black));
        mMiddlePaint = new Paint();
        mMiddlePaint.setColor(getResources().getColor(R.color.blue));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("LoadingView", "width:" + getWidth() + "  height:" + getHeight());
        int width = getWidth();
        int height = getHeight();
        int radius = DensityUtils.dp2px(getContext(), 23);
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);
        int left = width / 2 - DensityUtils.dp2px(getContext(), 23);
        int right = height / 2 + DensityUtils.dp2px(getContext(), 23);
        RectF recfF = new RectF(left, left, right, right);
        canvas.drawArc(recfF, 270, 30, true, mMiddlePaint);
        radius = DensityUtils.dp2px(getContext(), 21);
        canvas.drawCircle(width / 2, height / 2, radius, mInnerPaint);
    }
}
