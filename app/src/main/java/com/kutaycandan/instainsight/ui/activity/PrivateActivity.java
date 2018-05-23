package com.kutaycandan.instainsight.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.kutaycandan.instainsight.widget.textview.HurmeRegularTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivateActivity extends BaseActivity {

    @BindView(R.id.tv_insta)
    HurmeRegularTextView tvInsta;
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;
    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        ButterKnife.bind(this);
        getStalkBalance();
        new CountDownTimer(4000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                tvMyStalks.setText("My stalks: " + SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));

            }
            @Override
            public void onFinish() {
                tvMyStalks.setText("My stalks: " + SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));

            }
        }.start();
        tvMyStalks.setText("My stalks: " + SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));
    }

    public static void newIntent(Activity activity) {
        Intent intent = new Intent(activity, PrivateActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ll_refresh)
    public void onViewClicked() {
        finish();
    }
}
