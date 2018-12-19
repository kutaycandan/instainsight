package com.mirvinstalk.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

public class InternetWarningActivity extends BaseActivity {

    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_warning);
        ButterKnife.bind(this);

    }

    public static void newIntent(Activity activity) {
        Intent intent = new Intent(activity, InternetWarningActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }
    @OnClick(R.id.ll_refresh)
    public void tryLoginAgain(){
        Utils.startApp(this);
    }
}
