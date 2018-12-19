package com.mirvinstalk.app.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.mirvinstalk.app.R;

import com.mirvinstalk.app.InstaInsightApplication;
import com.mirvinstalk.app.constants.ServiceConstant;
import com.mirvinstalk.app.constants.SharedPrefsConstant;
import com.mirvinstalk.app.model.request.GetStalkBalanceRequest;
import com.mirvinstalk.app.model.response.BaseResponse;
import com.mirvinstalk.app.service.InstaInsightService;
import com.mirvinstalk.app.service.RestApi;
import com.mirvinstalk.app.util.SharedPrefsHelper;

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


}