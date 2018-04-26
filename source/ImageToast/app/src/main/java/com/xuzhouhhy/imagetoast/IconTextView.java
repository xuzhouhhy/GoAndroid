package com.xuzhouhhy.imagetoast;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class IconTextView extends AppCompatTextView {

    public IconTextView(Context context) {
        super(context);
        init(context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        setTypeface(iconfont);
        setIncludeFontPadding(false);
    }
}
