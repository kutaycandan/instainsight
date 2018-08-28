package com.kutaycandan.instainsight.ui.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.BillingConstant;
import com.kutaycandan.instainsight.constants.ServiceConstant;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.model.IIInvoice;
import com.kutaycandan.instainsight.model.IIInvoices;
import com.kutaycandan.instainsight.model.request.GetStalkBalanceRequest;
import com.kutaycandan.instainsight.model.request.LoginByCookieRequest;
import com.kutaycandan.instainsight.model.request.RegisterRequest;
import com.kutaycandan.instainsight.model.request.StalkAmountRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.RegisterResponse;
import com.kutaycandan.instainsight.model.response.ServiceUrlResponse;
import com.kutaycandan.instainsight.model.response.StalkAmountResponse;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements BillingProcessor.IBillingHandler, SearchBarFragment.SearchBarListener, SpendCoinFragment.SpendCoinListener, GetCoinFragment.GetCoinListener {

    private static final int NUM_PAGES = 2;

    ArrayList<IIInvoices> iiInvoices;
    ArrayList<String> names = new ArrayList<String>();
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

    Activity activity;
    int amount;

    //7985b54f6317e41a
    //7985b54f6317e41a

    //Override methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activity = this;
        isConnected = false;
        //SharedPrefsHelper.getInstance().clearAllData();
        getServiceUrl();
        purchaseInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingProcessor != null) {
            billingProcessor.release();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this,"You did2!",Toast.LENGTH_LONG).show();
        //if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
        //   super.onActivityResult(requestCode, resultCode, data);
        //}
        //else{
        //    billingProcessor.consumePurchase("android.test.purchased");
        //    Toast.makeText(getActivity(),"You did!",Toast.LENGTH_LONG).show();
        // }
        if (requestCode == ServiceConstant.REQUEST_ID) {
            getStalkBalance();
            View view = this.getCurrentFocus();
            if (view != null) {
                Utils.hideSoftKeyboard(this);
            }
        }
        else if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        /*else{
            billingProcessor.consumePurchase("android.test.purchased");
            Toast.makeText(this,"You consume!",Toast.LENGTH_LONG).show();
        }*/

    }


    // ----- Purchase functions starts -----------

    BillingProcessor billingProcessor;

    private void purchaseInit(){
        billingProcessor = new BillingProcessor(this, BillingConstant.BASE_64_CODE,this);
    }

    @Override
    public void get3coin() {
        billingProcessor.purchase(this, "stalkcoin3");
    }

    @Override
    public void get5coin() {
        billingProcessor.purchase(this, "stalkcoin5");
    }

    @Override
    public void get10coin() {
        billingProcessor.purchase(this, "stalkcoin10");
    }

    @Override
    public void get25coin() {
        billingProcessor.purchase(this, "stalkcoin25");
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        billingProcessor.consumePurchase(productId);
        //if(productId.equals("android.test.purchased")){
            //Toast.makeText(activity,"You consume test!",Toast.LENGTH_LONG).show();
        //}
        int stalkAmount=0;
        double moneyAmount=0;
        if(productId.equals("stalkcoin3")){
            stalkAmount = 3;
            moneyAmount = 1.99;
        }
        else if(productId.equals("stalkcoin5")){
            stalkAmount = 5;
            moneyAmount = 2.99;
        }
        else if(productId.equals("stalkcoin10")){
            stalkAmount = 10;
            moneyAmount = 4.99;
        }else if(productId.equals("stalkcoin25")){
            stalkAmount = 25;
            moneyAmount = 6.99;
        }
        StalkAmountRequest stalkAmountRequest = new StalkAmountRequest();
        stalkAmountRequest.setCookie(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.COOKIE_CODE, ""));
        stalkAmountRequest.setToken(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE, ""));
        stalkAmountRequest.setPurchaseKey(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.PURCHASE_KEY_CODE, ""));
        stalkAmountRequest.setPurchasedMoneyAmount(moneyAmount);
        stalkAmountRequest.setPurchasedMoneyCurrency("Dollar");
        stalkAmountRequest.setVersionCode("testandroid");
        stalkAmountRequest.setUserCode(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.USER_CODE, ""));
        stalkAmountRequest.setStalkAmount(stalkAmount);
        stalkAmountRequest.setStalkBalance(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE, 0));
        Call<BaseResponse<StalkAmountResponse>> call = instaInsightService.postStalk(stalkAmountRequest);
        call.enqueue(new Callback<BaseResponse<StalkAmountResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<StalkAmountResponse>> call, Response<BaseResponse<StalkAmountResponse>> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(activity,"You buy test!",Toast.LENGTH_LONG).show();
                    SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE, response.body().getData().getStalkAmount());
                    BusStation.getBus().post("Stalk:" + response.body().getData().getStalkAmount());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<StalkAmountResponse>> call, Throwable t) {

            }
        });
        //Toast.makeText(this,"You consume!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(this,"You couldn't get the coin!",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBillingInitialized() {

    }

    // -------------------------------------------


    private void getServiceUrl() {
        Call<ServiceUrlResponse> call = RestApi.getServerInstance(this).getUrl();
        call.enqueue(new Callback<ServiceUrlResponse>() {
            @Override
            public void onResponse(Call<ServiceUrlResponse> call, Response<ServiceUrlResponse> response) {
                if (response.isSuccessful()) {
                    String s = response.body().getServiceUrl();
                    SharedPrefsHelper.getInstance().save(SharedPrefsConstant.STRING_URL, s);
                    instaInsightService = RestApi.getInstance(activity);
                    if (Utils.isNetworkAvailable(activity)) {
                        logIn();
                    } else {

                        ivSwipe.setVisibility(View.GONE);
                        initPager(-1);
                        animation(tvInsta);
                    }

                }
            }

            @Override
            public void onFailure(Call<ServiceUrlResponse> call, Throwable t) {

            }
        });

    }


    private void goInternetWarning() {
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
                BusStation.getBus().post("Stalk:" + response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {

            }
        });
    }

    public void animation(final TextView view) {
        ValueAnimator va = ValueAnimator.ofInt(500, 240);
        int mDuration = 1500; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                llp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                llp.setMargins(0, (int) animation.getAnimatedValue(), 0, 0); // llp.setMargins(left, top, right, bottom);
                view.setLayoutParams(llp);
            }

        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

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
                        amount = response.body().getData().getIIUser().getAmount();
                        animation(tvInsta);
                        isConnected = true;
                        initPager(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RegisterResponse>> call, Throwable t) {

            }
        });
    }


    /*@Override
    public void onBackPressed() {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            BusStation.getBus().post("goDown");
        }
        else
            super.onBackPressed();
    }*/

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
                        amount = response.body().getData().getIIUser().getAmount();
                        if (response.body().getData().getIIUser().getIIInvoices().size() > 0) {
                            iiInvoices = response.body().getData().getIIUser().getIIInvoices();
                        }
                        initPager(1);


                        animation(tvInsta);
                        isConnected = true;

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RegisterResponse>> call, Throwable t) {

            }
        });

    }

    public ArrayList<IIInvoices> getIiInvoices() {
        if (iiInvoices == null) {
            return new ArrayList<>();
        }
        return iiInvoices;
    }

    @Override
    public void askUserToSpendCoin(String username) {
        this.username = username;
        BusStation.getBus().post("showSpendCoin");
    }

    public void endDemo() {
        UserProfileActivity.newIntent(this, "instainsightapp");

    }

    public void startDemo() {
        BusStation.getBus().post("showDemo");
    }

    @Override
    public void isSpendAccepted(boolean isAccept) {
        if (isAccept) {
            BusStation.getBus().post("showSearchBar");
            if (amount > 0) {
                UserProfileActivity.newIntent(this, username);
            } else {
                Toast.makeText(this, "You are out of coins.", Toast.LENGTH_LONG).show();
                new CountDownTimer(1000, 100) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        vpMain.setCurrentItem(1);
                    }
                }.start();
            }
        } else {
            BusStation.getBus().post("notAccept");
        }
    }

    private void initPager(int textCode) {
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), textCode);
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


        public MyPagerAdapter(FragmentManager fm, int textCode) {
            super(fm);
            this.textCode = textCode;

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
            if (textCode == -1) {
                return 1;
            }
            return NUM_PAGES;
        }
    }


}
