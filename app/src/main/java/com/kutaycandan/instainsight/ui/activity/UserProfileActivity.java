package com.kutaycandan.instainsight.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.ServiceConstant;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.model.IIFeatureOrder;
import com.kutaycandan.instainsight.model.InstaUserProfileData;
import com.kutaycandan.instainsight.model.request.GetFeatureDataRequest;
import com.kutaycandan.instainsight.model.request.GetFeatureStatesRequest;
import com.kutaycandan.instainsight.model.request.OrderFeaturesRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.OrderFeaturesResponse;
import com.kutaycandan.instainsight.service.InstaInsightService;
import com.kutaycandan.instainsight.service.RestApi;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends BaseActivity {
    private static final String USERNAME_CODE = "USERNAME_CODE";
    String username;
    @BindView(R.id.iv_back_button)
    ImageView ivBackButton;
    @BindView(R.id.tv_coin_amount)
    HurmeBoldTextView tvCoinAmount;
    @BindView(R.id.civ_profile_image)
    CircleImageView civProfileImage;
    @BindView(R.id.tv_username)
    HurmeBoldTextView tvUsername;
    @BindView(R.id.tv_post_count)
    HurmeBoldTextView tvPostCount;
    @BindView(R.id.tv_followers_count)
    HurmeBoldTextView tvFollowersCount;
    @BindView(R.id.tv_following_count)
    HurmeBoldTextView tvFollowingCount;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    InstaInsightService instaInsightService;
    ArrayList<IIFeatureOrder> iiFeatureOrders;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        showDialog();
        Bundle extras = getIntent().getExtras();
        instaInsightService = RestApi.getInstance(this);
        amount=(int)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE)-1;
        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE,amount);
        tvCoinAmount.setText(String.valueOf(amount));
        if (extras != null) {
            username = extras.getString(USERNAME_CODE);
            orderFeaturesRequest();
        }


    }

    private void orderFeaturesRequest(){
        OrderFeaturesRequest orderFeaturesRequest = new OrderFeaturesRequest();
        orderFeaturesRequest.setInstaUsername(username);
        orderFeaturesRequest.setVersionCode(ServiceConstant.VERSION_CODE);
        orderFeaturesRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        orderFeaturesRequest.setUserCode((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.USER_CODE));
        Call<BaseResponse<OrderFeaturesResponse>> call = instaInsightService.orderFeaturesRequest(orderFeaturesRequest);
        call.enqueue(new Callback<BaseResponse<OrderFeaturesResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OrderFeaturesResponse>> call, Response<BaseResponse<OrderFeaturesResponse>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        iiFeatureOrders = response.body().getData().getIInvoice().getIIFeatureOrders();
                        isInstaUserInfoReady();
                    }
                    else{
                        if(response.body().getExceptionMessage().equals("04-PrivateAccountException")){
                            orderFeaturesRequest();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<OrderFeaturesResponse>> call, Throwable t) {

            }
        });

    }
    private void isInstaUserInfoReady(){
        String GUID = getGUID("InstaUserProfileData");
        if(!GUID.equals("")){
            getFeatureStatesRequest(GUID,1);
        }
    }
    private void getFeatureStatesRequest(final String GUID,final int code){
        final GetFeatureStatesRequest getFeatureStatesRequest = new GetFeatureStatesRequest();
        getFeatureStatesRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        ArrayList<String> iiFeatureOrderGuid = new ArrayList<String>();
        iiFeatureOrderGuid.add(GUID);
        getFeatureStatesRequest.setIIFeatureOrderGuids(iiFeatureOrderGuid);
        Call<BaseResponse<ArrayList<ArrayList<String>>>> call = instaInsightService.getFeatureStatesRequest(getFeatureStatesRequest);
        call.enqueue(new Callback<BaseResponse<ArrayList<ArrayList<String>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<ArrayList<String>>>> call, Response<BaseResponse<ArrayList<ArrayList<String>>>> response) {
                Log.d("Lol", "onResponse: Data Ready1");
                if (response.isSuccessful()){
                    Log.d("Lol", "onResponse: Data Ready2");
                    if(response.body().isSuccess()){
                        Log.d("Lol", "onResponse: Data Ready3");
                        if(response.body().getData().get(0).get(1).equals("Ready")){
                            Log.d("Lol", "onResponse: Data Ready");
                            if(code==1){
                                getInstaUserInfo(GUID,code);
                            }
                        }
                        else{
                            Log.d("Lol", "onResponse: Data Ready değil");

                            new CountDownTimer(3000, 1000) {

                                public void onTick(long millisUntilFinished) {

                                }

                                public void onFinish() {
                                    getFeatureStatesRequest(GUID,code);
                                }
                            }.start();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<ArrayList<String>>>> call, Throwable t) {

            }
        });
    }

    private String getGUID(String name){
        if(iiFeatureOrders==null){
            return "";
        }
        for (int i = 0; i < iiFeatureOrders.size(); i++) {
            if(iiFeatureOrders.get(i).getName().equals(name)){
                return iiFeatureOrders.get(i).getGUID();
            }
        }
        return "";
    }
    private void setInstaUserInfos(InstaUserProfileData instaUserInfos){
        Picasso.with(this)
                .load(instaUserInfos.getProfilePicture())
                .into(civProfileImage);
        tvFollowersCount.setText(""+instaUserInfos.getFollower());
        tvPostCount.setText(""+instaUserInfos.getPostCount());
        tvFollowingCount.setText(""+instaUserInfos.getFollowing());
        tvUsername.setText(instaUserInfos.getUserName());
    }
    private void getInstaUserInfo(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        Call<BaseResponse<BaseResponse<InstaUserProfileData>>> call = instaInsightService.getInstaUserProfileDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<InstaUserProfileData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<InstaUserProfileData>>> call, Response<BaseResponse<BaseResponse<InstaUserProfileData>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            setInstaUserInfos(response.body().getData().getData());
                            dismissDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<InstaUserProfileData>>> call, Throwable t) {

            }
        });


    }

    public static void newIntent(Activity activity, String username) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(USERNAME_CODE, username);
        activity.startActivity(intent);
    }
}
