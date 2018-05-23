package com.kutaycandan.instainsight.ui.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.ServiceConstant;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.model.IIInvoice;
import com.kutaycandan.instainsight.model.IIInvoices;
import com.kutaycandan.instainsight.model.request.LoginByCookieRequest;
import com.kutaycandan.instainsight.model.request.RegisterRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.RegisterResponse;
import com.kutaycandan.instainsight.service.InstaInsightService;
import com.kutaycandan.instainsight.service.RestApi;
import com.kutaycandan.instainsight.ui.fragment.GetCoinFragment;
import com.kutaycandan.instainsight.ui.fragment.SearchBarFragment;
import com.kutaycandan.instainsight.ui.fragment.SearchFragment;
import com.kutaycandan.instainsight.ui.fragment.SpendCoinFragment;
import com.kutaycandan.instainsight.util.BusStation;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.util.Utils;
import com.kutaycandan.instainsight.widget.textview.HurmeRegularTextView;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SearchBarFragment.SearchBarListener, SpendCoinFragment.SpendCoinListener {

    private static final int NUM_PAGES = 2;

    ArrayList<IIInvoices> iiInvoices;
    ArrayList<String> names=new ArrayList<String>();
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.iv_swipe)
    ImageView ivSwipe;
    @BindView(R.id.tv_insta)
    HurmeRegularTextView tvInsta;
    @BindView(R.id.rl_load)
    RelativeLayout rlLoad;

    private PagerAdapter mPagerAdapter;

    String cookie;
    String userCode;
    boolean isConnected;

    String username;

    InstaInsightService instaInsightService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        instaInsightService = RestApi.getInstance(this);
        isConnected=false;

        //SharedPrefsHelper.getInstance().clearAllData();
        if(Utils.isNetworkAvailable(this)){
            logIn();
        }
        else{

            ivSwipe.setVisibility(View.GONE);
            initPager(-1);
            animation(tvInsta);
        }
    }
    //7985b54f6317e41a
    //7985b54f6317e41a


    private void goInternetWarning(){
        InternetWarningActivity.newIntent(this);
    }

    private void logIn() {
        cookie = SharedPrefsHelper.getInstance().get(SharedPrefsConstant.COOKIE_CODE, "");
        userCode = SharedPrefsHelper.getInstance().get(SharedPrefsConstant.USER_CODE, "");

        if (cookie.equals("") || userCode.equals("")) {
            callRegisterRequest();
        } else {
            callLoginRequest();
        }

    }

    public void animation(final TextView view) {
        ValueAnimator va = ValueAnimator.ofInt(500, 240);
        int mDuration = 1500; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                llp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                llp.setMargins(0, (int)animation.getAnimatedValue(), 0, 0); // llp.setMargins(left, top, right, bottom);
                view.setLayoutParams(llp);
            }

        });
        va.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {

                    rlLoad.setVisibility(View.GONE);



            }
        });
        va.start();
    }

    private void callRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setVersionCode(ServiceConstant.VERSION_CODE);
        Call<BaseResponse<RegisterResponse>> call = instaInsightService.registerRequest(registerRequest);
        call.enqueue(new Callback<BaseResponse<RegisterResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<RegisterResponse>> call, Response<BaseResponse<RegisterResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.ADKEY_CODE, response.body().getData().getIIUser().getAdKey());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.COOKIE_CODE, response.body().getData().getIIUser().getCookie());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.TOKEN_CODE, response.body().getData().getIIUser().getToken());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.PURCHASE_KEY_CODE, response.body().getData().getIIUser().getPurchaseKey());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.USER_CODE, response.body().getData().getIIUser().getUserCode());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE, response.body().getData().getIIUser().getAmount());
                        animation(tvInsta);
                        isConnected=true;
                        initPager(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RegisterResponse>> call, Throwable t) {

            }
        });
    }

    private void callLoginRequest() {
        LoginByCookieRequest loginByCookieRequest = new LoginByCookieRequest();
        loginByCookieRequest.setVersionCode(ServiceConstant.VERSION_CODE);
        loginByCookieRequest.setCookie(cookie);
        loginByCookieRequest.setUserCode(userCode);
        Call<BaseResponse<RegisterResponse>> call = instaInsightService.loginRequest(loginByCookieRequest);
        call.enqueue(new Callback<BaseResponse<RegisterResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<RegisterResponse>> call, Response<BaseResponse<RegisterResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.ADKEY_CODE, response.body().getData().getIIUser().getAdKey());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.COOKIE_CODE, response.body().getData().getIIUser().getCookie());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.TOKEN_CODE, response.body().getData().getIIUser().getToken());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.PURCHASE_KEY_CODE, response.body().getData().getIIUser().getPurchaseKey());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.PERMA_CODE, response.body().getData().getIIUser().getPermaCode());
                        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE, response.body().getData().getIIUser().getAmount());
                        if (response.body().getData().getIIUser().getIIInvoices().size() > 0) {
                            iiInvoices = response.body().getData().getIIUser().getIIInvoices();
                        }
                        initPager(1);


                        animation(tvInsta);
                        isConnected=true;

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RegisterResponse>> call, Throwable t) {

            }
        });

    }

    public ArrayList<IIInvoices> getIiInvoices() {
        return iiInvoices;
    }

    @Override
    public void askUserToSpendCoin(String username) {
        this.username=username;
        BusStation.getBus().post("showSpendCoin");
    }

    public void endDemo(){
        UserProfileActivity.newIntent(this,"instainsightapp");

    }

    public void startDemo(){
        BusStation.getBus().post("showDemo");
    }

    @Override
    public void isSpendAccepted(boolean isAccept) {
        if(isAccept){
            BusStation.getBus().post("showSearchBar");
            UserProfileActivity.newIntent(this,username);
        }
        else{
            BusStation.getBus().post("notAccept");
        }
    }

    private void initPager(int textCode) {
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),textCode);
        vpMain.setAdapter(mPagerAdapter);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ivSwipe.setBackground(getResources().getDrawable(R.drawable.ic_swipe_button1));
                }
                if (position == 1) {
                    ivSwipe.setBackground(getResources().getDrawable(R.drawable.ic_swipe_button2));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    private class MyPagerAdapter extends FragmentPagerAdapter {
        int textCode;


        public MyPagerAdapter(FragmentManager fm,int textCode) {
            super(fm);
            this.textCode=textCode;

        }


        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return SearchFragment.newInstance(textCode);
                case 1:
                    return GetCoinFragment.newInstance(textCode);
                default:
                    return new Fragment();
            }
        }


        @Override
        public int getCount() {
            if(textCode==-1){
                return 1;
            }
            return NUM_PAGES;
        }
    }
}
