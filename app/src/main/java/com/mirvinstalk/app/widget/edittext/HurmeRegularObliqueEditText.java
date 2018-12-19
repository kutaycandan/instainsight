package com.mirvinstalk.app.widget.edittext;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

public class HurmeRegularObliqueEditText extends EditText {
    public HurmeRegularObliqueEditText(Context context) {
        super(context);
        setFont(context);
    }

    public HurmeRegularObliqueEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public HurmeRegularObliqueEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    public void setFont(Context context) {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "hurme_oblique.otf"));
    }
}