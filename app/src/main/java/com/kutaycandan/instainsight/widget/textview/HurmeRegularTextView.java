package com.kutaycandan.instainsight.widget.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class HurmeRegularTextView extends TextView {
    public HurmeRegularTextView(Context context) {
        super(context);
        setFont(context);
    }

    public HurmeRegularTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public HurmeRegularTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    public void setFont(Context context) {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "hurme_regular.otf"));
    }
}
