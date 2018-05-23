package com.kutaycandan.instainsight.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetCoinActivity extends BaseActivity {


    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.tv_3coin)
    HurmeBoldTextView tv3coin;
    @BindView(R.id.btn_3coin)
    LinearLayout btn3coin;
    @BindView(R.id.tv_8coin)
    HurmeBoldTextView tv8coin;
    @BindView(R.id.btn_8coin)
    LinearLayout btn8coin;
    @BindView(R.id.tv_30coin)
    HurmeBoldTextView tv30coin;
    @BindView(R.id.btn_30coin)
    LinearLayout btn30coin;
    @BindView(R.id.tv_100coin)
    HurmeBoldTextView tv100coin;
    @BindView(R.id.btn_100coin)
    LinearLayout btn100coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coin);
        ButterKnife.bind(this);

    }

    public static void newIntent(Activity activity) {
        Intent intent = new Intent(activity, GetCoinActivity.class);
        activity.startActivity(intent);
    }

}
