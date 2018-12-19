package com.mirvinstalk.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.gson.Gson;
import com.mirvinstalk.app.R;
import com.mirvinstalk.app.constants.BillingConstant;
import com.mirvinstalk.app.constants.ServiceConstant;
import com.mirvinstalk.app.constants.SharedPrefsConstant;
import com.mirvinstalk.app.model.request.StalkAmountRequest;
import com.mirvinstalk.app.model.response.BaseResponse;
import com.mirvinstalk.app.model.response.StalkAmountResponse;
import com.mirvinstalk.app.util.BusStation;
import com.mirvinstalk.app.util.SharedPrefsHelper;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCoinActivity extends BaseActivity implements BillingProcessor.IBillingHandler{


    @BindView(R.id.tv_my_stalks)
    HurmeBoldTextView tvMyStalks;
    @BindView(R.id.tv_3coin)
    HurmeBoldTextView tv3coin;
    @BindView(R.id.btn_3coin)
    LinearLayout btn3coin;
    @BindView(R.id.tv_5coin)
    HurmeBoldTextView tv5coin;
    @BindView(R.id.btn_5coin)
    LinearLayout btn5coin;
    @BindView(R.id.tv_10coin)
    HurmeBoldTextView tv10coin;
    @BindView(R.id.btn_10coin)
    LinearLayout btn10coin;
    @BindView(R.id.tv_25coin)
    HurmeBoldTextView tv25coin;
    @BindView(R.id.btn_25coin)
    LinearLayout btn25coin;

    Activity activity;

    String currencyType;
    ArrayList<ArrayList<Double>> prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coin);
        activity = this;
        ButterKnife.bind(this);
        tvMyStalks.setText("My stalks: 0");
        purchaseInit();
        setClickListeners();
        setPrices();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingProcessor != null) {
            billingProcessor.release();
        }
    }
    public void setPrices(){
        Log.d("Locale",""+ Locale.getDefault().getDisplayCountry());
        Currency currency = Currency.getInstance(Locale.getDefault());
        currencyType = currency.getCurrencyCode();
        if(currencyType.equalsIgnoreCase("TRY")){
            prices = SharedPrefsHelper.getInstance().getArrayList(SharedPrefsConstant.PRICE_IN_TRY);
        }
        else if(currencyType.equalsIgnoreCase("EUR")){
            prices = SharedPrefsHelper.getInstance().getArrayList(SharedPrefsConstant.PRICE_IN_EUR);
        }else{
            currencyType="USD";
            prices = SharedPrefsHelper.getInstance().getArrayList(SharedPrefsConstant.PRICE_IN_USD);
        }
        if(prices!=null){
            if(prices.size()==4){
                tv3coin.setText(""+prices.get(0).get(1)+" "+currencyType);
                tv5coin.setText(""+prices.get(1).get(1)+" "+currencyType);
                tv10coin.setText(""+prices.get(2).get(1)+" "+currencyType);
                tv25coin.setText(""+prices.get(3).get(1)+" "+currencyType);
            }
        }
    }

    public void setClickListeners() {
        btn3coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get3coin();
            }
        });
        btn5coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get5coin();
            }
        });
        btn10coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get10coin();
            }
        });
        btn25coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get25coin();
            }
        });
    }

    // ----- Purchase functions starts -----------

    BillingProcessor billingProcessor;

    private void purchaseInit(){
        billingProcessor = new BillingProcessor(this, BillingConstant.BASE_64_CODE,this);
    }

    public void get3coin() {
        billingProcessor.purchase(this, "stalkcoin3");
    }

    public void get5coin() {
        billingProcessor.purchase(this, "stalkcoin5");
    }

    public void get10coin() {
        billingProcessor.purchase(this, "stalkcoin10");
    }

    public void get25coin() {
        billingProcessor.purchase(this, "stalkcoin25");
    }

    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        billingProcessor.consumePurchase(productId);
        //if(productId.equals("android.test.purchased")){
        //Toast.makeText(activity,"You consume test!",Toast.LENGTH_LONG).show();
        //}
        Gson gson = new Gson();
        String json = gson.toJson(details);
        Log.d("transactionDetail",json);
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
                    Toast.makeText(activity,"Purchase granted!",Toast.LENGTH_LONG).show();
                    SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE, response.body().getData().getStalkAmount());
                    tvMyStalks.setText("My stalks: "+response.body().getData().getStalkAmount());
                    //BusStation.getBus().post("Stalk:" + response.body().getData().getStalkAmount());
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
        //Toast.makeText(this,"You couldn't get the coin!",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBillingInitialized() {

    }

    // -------------------------------------------


    public static void newIntent(Activity activity) {
        Intent intent = new Intent(activity, GetCoinActivity.class);
        activity.startActivityForResult(intent, ServiceConstant.REQUEST_ID);

    }

}
