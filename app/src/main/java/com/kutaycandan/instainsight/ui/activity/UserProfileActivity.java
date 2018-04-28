package com.kutaycandan.instainsight.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kutaycandan.instainsight.R;
import com.kutaycandan.instainsight.constants.ServiceConstant;
import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.model.IIFeatureOrder;
import com.kutaycandan.instainsight.model.InstaUserModel;
import com.kutaycandan.instainsight.model.InstaUserProfileData;
import com.kutaycandan.instainsight.model.request.GetFeatureDataRequest;
import com.kutaycandan.instainsight.model.request.GetFeatureStatesRequest;
import com.kutaycandan.instainsight.model.request.OrderFeaturesRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.OrderFeaturesResponse;
import com.kutaycandan.instainsight.service.InstaInsightService;
import com.kutaycandan.instainsight.service.RestApi;
import com.kutaycandan.instainsight.ui.dialog.SpendCoinDialog;
import com.kutaycandan.instainsight.ui.fragment.ButtonFragment;
import com.kutaycandan.instainsight.ui.fragment.ChartFragment;
import com.kutaycandan.instainsight.ui.fragment.FeatureFragment;
import com.kutaycandan.instainsight.ui.fragment.LoadingFragment;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;
import com.kutaycandan.instainsight.widget.textview.HurmeBoldTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends BaseActivity implements ButtonFragment.ButtonFragmentListener{
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
    int fragmentType;
    int amount;
    Activity activity;
    FragmentTransaction transaction;
    FragmentManager manager;
    String buttonName;
    ArrayList<InstaUserModel> instaUserModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        showDialog();
        Bundle extras = getIntent().getExtras();
        instaInsightService = RestApi.getInstance(this);
        activity=this;
        if (extras != null) {
            username = extras.getString(USERNAME_CODE);
            orderFeaturesRequest();
        }
        getButtonFragment();
        setAmount();



    }

    private void setAmount(){
        tvCoinAmount.setText(""+SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE));
    }

    // ---- Fragment Call Functions
    private void getButtonFragment(){
        ButtonFragment bf = new ButtonFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,bf);
        transaction.commit();
        fragmentType=1;
    }
    private void getLoadingFragment(){
        LoadingFragment lf = new LoadingFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,lf);
        transaction.commit();
        fragmentType=2;
    }
    private void getFeatureFragment(String s){
        Bundle bundle = new Bundle();
        bundle.putString("featureName", s);
        FeatureFragment ff = new FeatureFragment();
        ff.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,ff);
        transaction.commit();
        fragmentType=3;
    }

    private void getChartFragment(ArrayList<Integer> likeList){
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("likeList", likeList);
        ChartFragment cf = new ChartFragment();
        cf.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,cf);
        transaction.commit();
        fragmentType=3;
    }


    // ---- Fragment Functions ends


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
                            SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE,amount+1);
                            finish();
                            PrivateActivity.newIntent(activity);
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
                if (response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().get(0).get(1).equals("Ready")){
                            if(code==1){
                                getInstaUserInfo(GUID,code);
                            }
                            if(code==2){
                                getFeatures(GUID,code);
                            }
                            if(code==3){
                                getChart(GUID,code);
                            }
                        }
                        else{
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

    @OnClick(R.id.iv_back_button)
    public void goBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(fragmentType==1){
            finish();
        }
        if(fragmentType==2 || fragmentType==3){
            getButtonFragment();
        }
        else{
            super.onBackPressed();
        }
    }
    public ArrayList<InstaUserModel> getUserList(){
        return instaUserModelArrayList;
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
        amount=(int)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.AMOUNT_CODE)-1;
        SharedPrefsHelper.getInstance().save(SharedPrefsConstant.AMOUNT_CODE,amount);
        tvCoinAmount.setText(String.valueOf(amount));
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

    private void getChart(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> call = instaInsightService.getLikeTrendDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<Integer>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> call, Response<BaseResponse<BaseResponse<ArrayList<Integer>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            getChartFragment(response.body().getData().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> call, Throwable t) {

            }
        });


        /*Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call =  instaInsightService.getFeatureDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call, Response<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            instaUserModelArrayList = response.body().getData().getData();
                            getFeatureFragment(buttonName);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call, Throwable t) {

            }
        });*/
    }

    private void getFeatures(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call =  instaInsightService.getFeatureDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call, Response<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            instaUserModelArrayList = response.body().getData().getData();
                            getFeatureFragment(buttonName);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call, Throwable t) {

            }
        });
    }


    public void searchUserInRV(String s){
            manager = getSupportFragmentManager();
            transaction= manager.beginTransaction();
            SpendCoinDialog spendCoinDialog = SpendCoinDialog.newInstance(s);
            spendCoinDialog.show(transaction, "dialog");

    }

    public static void newIntent(Activity activity, String username) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(USERNAME_CODE, username);
        activity.startActivity(intent);
    }

    @Override
    public void nonFollowersClicked() {

    }

    @Override
    public void profileStalkersClicked() {

    }

    @Override
    public void nonFollowingClicked() {

    }

    @Override
    public void topLikersClicked() {

    }

    @Override
    public void newFollowersClicked() {
        getLoadingFragment();
        buttonName = "New Followers";
        String GUID = getGUID(ServiceConstant.NEW_FOLLOWERS);
        getFeatureStatesRequest(GUID,2);
    }

    @Override
    public void mostLikesSentClicked() {

    }

    @Override
    public void newFollowingClicked() {

    }

    @Override
    public void popularfollowersClicked() {

    }

    @Override
    public void likeTrendClicked() {
        getLoadingFragment();
        String GUID = getGUID(ServiceConstant.LIKE_TREND);
        getFeatureStatesRequest(GUID,3);
    }

    @Override
    public void stalkCountCliked() {

    }
}
