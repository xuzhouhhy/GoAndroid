package com.xuzhouhhy.progressview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ClipLoadingView extends View {

    private Paint mPaint;

    public ClipLoadingView(Context context) {
        super(context);
        initPaint();
    }

    public ClipLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ClipLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawLine(10, 10, 300, 300, mPaint);
//        canvas.drawArc();
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.loading),10,20,mPaint);
    }
}
