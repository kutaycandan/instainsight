package com.kutaycandan.instainsight.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.ServiceConstant;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.model.request.GetStalkBalanceRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.kutaycandan.instainsight.widget.textview.HurmeRegularTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivateActivity extends BaseActivity {

    @BindView(R.id.tv_insta)
    HurmeRegularTextView tvInsta;
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;
    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.text1)
    HurmeRegularTextView text1;
    @BindView(R.id.text2)
    HurmeRegularTextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        ButterKnife.bind(this);
        int amount = SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE, 0);
        if(amount !=0)
            tvMyStalks.setText("My stalks: " + amount);
        getStalkBalance();
        if (getIntent().getIntExtra(ServiceConstant.PRIVATE_CODE, 0) != 0) {
            if (getIntent().getIntExtra(ServiceConstant.PRIVATE_CODE, 0) == 5) {
                text1.setText("Sorry, this profile is too popular");
                text2.setText("to be stalked.");
            }
            if (getIntent().getIntExtra(ServiceConstant.PRIVATE_CODE, 0) == 4) {
                text1.setText("Sorry, you can't stalk this profile");
                text2.setText("as it is private.");
            }
        }

    }

    public void getStalkBalance() {
        GetStalkBalanceRequest getStalkBalanceRequest = new GetStalkBalanceRequest();
        getStalkBalanceRequest.setToken(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE, ""));
        getStalkBalanceRequest.setVersionCode(ServiceConstant.VERSION_CODE);
        getStalkBalanceRequest.setUserCode(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.USER_CODE, ""));
        Call<BaseResponse<Integer>> call = instaInsightService.getStalkBalance(getStalkBalanceRequest);
        call.enqueue(new Callback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE, response.body().getData());
                tvMyStalks.setText("My stalks: " + response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {

            }
        });
    }


    public static void newIntent(Activity activity, int code) {
        Intent intent = new Intent(activity, PrivateActivity.class);
        intent.putExtra(ServiceConstant.PRIVATE_CODE, code);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ll_refresh)
    public void onViewClicked() {
        finish();
    }
}
