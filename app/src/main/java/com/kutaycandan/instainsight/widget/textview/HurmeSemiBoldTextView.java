package com.kutaycandan.instainsight.widget.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class HurmeSemiBoldTextView extends TextView {
    public HurmeSemiBoldTextView(Context context) {
        super(context);
        setFont(context);
    }

    public HurmeSemiBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public HurmeSemiBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    public void setFont(Context context) {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "hurme_semi_bold.otf"));
    }
}
