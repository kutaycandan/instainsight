package com.kutaycandan.instainsight.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.kutaycandan.instainsight.R;

import com.kutaycandan.instainsight.InstaInsightApplication;
import com.kutaycandan.instainsight.constants.ServiceConstant;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.model.request.GetStalkBalanceRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.service.InstaInsightService;
import com.kutaycandan.instainsight.service.RestApi;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class BaseActivity extends AppCompatActivity {

    Dialog dialog;
    InstaInsightService instaInsightService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instaInsightService = RestApi.getInstance(this);

    }

    public void showDialog() {
        if (!BaseActivity.this.isFinishing()) {
            dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_loading);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    public void deleteSharedPrefs(){
        SharedPrefsHelper.getInstance().clearAllData();
    }

    public void getStalkBalance(){
        GetStalkBalanceRequest getStalkBalanceRequest = new GetStalkBalanceRequest();
        getStalkBalanceRequest.setToken(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE, ""));
        getStalkBalanceRequest.setVersionCode(ServiceConstant.VERSION_CODE);
        getStalkBalanceRequest.setUserCode(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.USER_CODE,""));
        Call<BaseResponse<Integer>> call = instaInsightService.getStalkBalance(getStalkBalanceRequest);
        call.enqueue(new Callback<BaseResponse<Integer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE,response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {

            }
        });
    }
}