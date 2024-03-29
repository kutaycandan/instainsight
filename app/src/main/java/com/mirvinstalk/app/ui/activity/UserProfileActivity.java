package com.mirvinstalk.app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirvinstalk.app.R;
import com.mirvinstalk.app.constants.ServiceConstant;
import com.mirvinstalk.app.constants.SharedPrefsConstant;
import com.mirvinstalk.app.model.HdProfilePicDto;
import com.mirvinstalk.app.model.IIFeatureOrder;
import com.mirvinstalk.app.model.InstaUserModel;
import com.mirvinstalk.app.model.InstaUserModelFollowerCount;
import com.mirvinstalk.app.model.InstaUserModelLikeCount;
import com.mirvinstalk.app.model.InstaUserProfileData;
import com.mirvinstalk.app.model.LatestPopularPostDto;
import com.mirvinstalk.app.model.request.GetFeatureDataRequest;
import com.mirvinstalk.app.model.request.GetFeatureStatesRequest;
import com.mirvinstalk.app.model.request.GetStalkBalanceRequest;
import com.mirvinstalk.app.model.request.OrderFeaturesRequest;
import com.mirvinstalk.app.model.response.BaseResponse;
import com.mirvinstalk.app.model.response.OrderFeaturesResponse;
import com.mirvinstalk.app.service.InstaInsightService;
import com.mirvinstalk.app.service.RestApi;
import com.mirvinstalk.app.ui.dialog.SpendCoinDialog;
import com.mirvinstalk.app.ui.fragment.ButtonFragment;
import com.mirvinstalk.app.ui.fragment.ChartFragment;
import com.mirvinstalk.app.ui.fragment.FeatureFragment;
import com.mirvinstalk.app.ui.fragment.HDProfilePictureFragment;
import com.mirvinstalk.app.ui.fragment.LatestPopularPostFragment;
import com.mirvinstalk.app.ui.fragment.LoadingFragment;
import com.mirvinstalk.app.ui.fragment.StalkCountFragment;
import com.mirvinstalk.app.util.BusStation;
import com.mirvinstalk.app.util.SharedPrefsHelper;
import com.mirvinstalk.app.widget.textview.HurmeBoldTextView;
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
    Activity activity;
    FragmentTransaction transaction;
    FragmentManager manager;
    String buttonName;
    ArrayList<InstaUserModel> instaUserModelArrayList;
    ArrayList<InstaUserModelLikeCount> instaUserModelLikeCountArrayList;
    ArrayList<InstaUserModelFollowerCount> instaUserModelFollowerCountArrayList;


    int amount;
    String lastCalledGUID;
    boolean isDialogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        fragmentType=1;
        showDialog();
        isDialogShown = true;
        Bundle extras = getIntent().getExtras();
        instaInsightService = RestApi.getInstance(this);
        activity=this;
        if (extras != null) {
            username = extras.getString(USERNAME_CODE);
            orderFeaturesRequest();
        }
        getButtonFragment();

    }

    public int getAmount(){
        return amount;
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
                tvCoinAmount.setText(""+response.body().getData());
                amount=response.body().getData();
            }

            @Override
            public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {

            }
        });
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
        int type=1;
        bundle.putInt("type",type);
        bundle.putString("featureName", s);
        FeatureFragment ff = new FeatureFragment();
        ff.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,ff);
        transaction.commit();
        fragmentType=3;
    }

    private void getFeatureFragmentWithLikeCount(String s){
        Bundle bundle = new Bundle();
        int type=2;
        bundle.putInt("type",type);
        bundle.putString("featureName", s);
        FeatureFragment ff = new FeatureFragment();
        ff.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,ff);
        transaction.commit();
        fragmentType=3;
    }
    private void getFeatureFragmentWithFollowerCount(String s){
        Bundle bundle = new Bundle();
        int type=3;
        bundle.putInt("type",type);
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

    private void getStalkCountFragment(Integer stalkCount){
        Bundle bundle = new Bundle();
        bundle.putInt("stalkCount", stalkCount);
        StalkCountFragment scf = new StalkCountFragment();
        scf.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,scf);
        transaction.commit();
        fragmentType=3;
    }

    private void getLatestPopularPostFragment(ArrayList<LatestPopularPostDto> latestPopularPostDtoArrayList){
        Bundle bundle = new Bundle();
        bundle.putSerializable("latestPopularPost", latestPopularPostDtoArrayList);
        LatestPopularPostFragment lppf = new LatestPopularPostFragment();
        lppf.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,lppf);
        transaction.commit();
        fragmentType=3;
    }

    private void getHDProfilePictureFragment(HdProfilePicDto hdProfilePicDto){
        Bundle bundle = new Bundle();
        bundle.putSerializable("hdProfilePicDto", hdProfilePicDto);
        HDProfilePictureFragment hppf = new HDProfilePictureFragment();
        hppf.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container,hppf);
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
                            finish();
                            PrivateActivity.newIntent(activity,4);
                        }
                        else  if(response.body().getExceptionMessage().equals("05-FamousAccountException")){
                            finish();
                            PrivateActivity.newIntent(activity,5);
                        }
                        else{

                        }

                    }
                }
                if(response.code()==500){
                    Toast.makeText(activity,"User not found.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<OrderFeaturesResponse>> call, Throwable t) {
                dismissDialog();
                isDialogShown = false;
                Toast.makeText(activity,"User not found.",Toast.LENGTH_LONG).show();
                finish();
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
                            if(code==4){
                                getStalkCount(GUID,code);
                            }
                            if(code==5){
                                getFeaturesWithLikeCount(GUID,code);
                            }
                            if(code==6){
                                getFeaturesWithFollowerCount(GUID,code);
                            }
                            if(code==7){
                                getLatestPopularPost(GUID,code);
                            }
                            if(code==8){
                                getHDProfilePic(GUID,code);
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
    public ArrayList<InstaUserModelLikeCount> getUserListWithLikeCount(){
        return instaUserModelLikeCountArrayList;
    }
    public ArrayList<InstaUserModelFollowerCount> getUserListWithFollowerCount(){
        return instaUserModelFollowerCountArrayList;
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
                            getStalkBalance();
                            setInstaUserInfos(response.body().getData().getData());
                            dismissDialog();
                            isDialogShown = false;
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
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> call = instaInsightService.getLikeTrendDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<Integer>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> call, Response<BaseResponse<BaseResponse<ArrayList<Integer>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
                            getChartFragment(response.body().getData().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> call, Throwable t) {

            }
        });
    }

    private void getStalkCount(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<Integer>>> call = instaInsightService.getStalkedCountDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<Integer>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<Integer>>> call, Response<BaseResponse<BaseResponse<Integer>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
                            getStalkCountFragment(response.body().getData().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<Integer>>> call, Throwable t) {

            }
        });
    }

    private void getLatestPopularPost(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<ArrayList<LatestPopularPostDto>>>> call = instaInsightService.getLatestPopularPostsDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<LatestPopularPostDto>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<LatestPopularPostDto>>>> call, Response<BaseResponse<BaseResponse<ArrayList<LatestPopularPostDto>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
                            getLatestPopularPostFragment(response.body().getData().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<LatestPopularPostDto>>>> call, Throwable t) {

            }
        });
    }
    private void getHDProfilePic(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<HdProfilePicDto>>> call = instaInsightService.getHdProfilePicture(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<HdProfilePicDto>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<HdProfilePicDto>>> call, Response<BaseResponse<BaseResponse<HdProfilePicDto>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
                            getHDProfilePictureFragment(response.body().getData().getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<HdProfilePicDto>>> call, Throwable t) {

            }
        });
    }

    private void getFeatures(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call =  instaInsightService.getFeatureDataRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> call, Response<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            instaUserModelArrayList = response.body().getData().getData();
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
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
    private void getFeaturesWithFollowerCount(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelFollowerCount>>>> call =  instaInsightService.getPopularFollowersRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<InstaUserModelFollowerCount>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelFollowerCount>>>> call, Response<BaseResponse<BaseResponse<ArrayList<InstaUserModelFollowerCount>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            instaUserModelFollowerCountArrayList = response.body().getData().getData();
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
                            getFeatureFragmentWithFollowerCount(buttonName);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelFollowerCount>>>> call, Throwable t) {

            }
        });
    }

    private void getFeaturesWithLikeCount(final String GUID,final int code){
        GetFeatureDataRequest getFeatureDataRequest = new GetFeatureDataRequest();
        getFeatureDataRequest.setToken((String)SharedPrefsHelper.getInstance().get(SharedPrefsConstant.TOKEN_CODE));
        getFeatureDataRequest.setIIFeatureOrderGuid(GUID);
        lastCalledGUID = GUID;
        Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelLikeCount>>>> call =  instaInsightService.getLikeCountRequest(getFeatureDataRequest);
        call.enqueue(new Callback<BaseResponse<BaseResponse<ArrayList<InstaUserModelLikeCount>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelLikeCount>>>> call, Response<BaseResponse<BaseResponse<ArrayList<InstaUserModelLikeCount>>>> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        if(response.body().getData().isSuccess()){
                            instaUserModelLikeCountArrayList = response.body().getData().getData();
                            if(fragmentType==1 || fragmentType == 3 || lastCalledGUID!=GUID){
                                return;
                            }
                            getFeatureFragmentWithLikeCount(buttonName);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelLikeCount>>>> call, Throwable t) {

            }
        });
    }


    public void searchUserInRV(String s){
        if(amount>0){
            manager = getSupportFragmentManager();
            transaction= manager.beginTransaction();
            SpendCoinDialog spendCoinDialog = SpendCoinDialog.newInstance(s);
            spendCoinDialog.show(transaction, "dialog");
        }
        else {
            Toast.makeText(this,"You are out of coins.",Toast.LENGTH_LONG).show();
            new CountDownTimer(3000,100){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    GetCoinActivity.newIntent(activity);
                }
            }.start();
        }


    }

    public static void newIntent(Activity activity, String username) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(USERNAME_CODE, username);
        activity.startActivityForResult(intent,ServiceConstant.REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        getStalkBalance();
    }

    @Override
    public void hdProfilePictureClicked() {
        getLoadingFragment();
        String GUID = getGUID(ServiceConstant.HD_PROFILE_PICTURE);
        getFeatureStatesRequest(GUID,8);
    }

    @Override
    public void profileStalkersClicked() {
        getLoadingFragment();
        buttonName = "Profile Stalkers";
        String GUID = getGUID(ServiceConstant.PROFILE_STALKERS);
        getFeatureStatesRequest(GUID,2);
    }

    @Override
    public void nonFollowingClicked() {
        getLoadingFragment();
        buttonName = "Non Following";
        String GUID = getGUID(ServiceConstant.NON_FOLLOWING);
        getFeatureStatesRequest(GUID,2);
    }

    @Override
    public void topLikersClicked() {
        getLoadingFragment();
        buttonName = "Top Likers";
        String GUID = getGUID(ServiceConstant.TOP_LIKERS);
        getFeatureStatesRequest(GUID,5);
    }

    @Override
    public void mostActiveFollowersClicked() {
        getLoadingFragment();
        buttonName = "Most Active Followers";
        String GUID = getGUID(ServiceConstant.ACTIVE_FOLLOWERS);
        getFeatureStatesRequest(GUID,2);
    }

    @Override
    public void latestPopularPostClicked() {
        getLoadingFragment();
        buttonName = "Latest Popular Posts";
        String GUID = getGUID(ServiceConstant.LATEST_POPULAR_POSTS);
        getFeatureStatesRequest(GUID,7);
    }

    @Override
    public void nonFollowersClicked() {
        getLoadingFragment();
        buttonName = "Non Followers";
        String GUID = getGUID(ServiceConstant.NON_FOLLOWERS);
        getFeatureStatesRequest(GUID,2);
    }

    @Override
    public void popularfollowersClicked() {
        getLoadingFragment();
        buttonName = "Popular Followers";
        String GUID = getGUID(ServiceConstant.POPULAR_FOLLOWERS);
        getFeatureStatesRequest(GUID,6);
    }

    @Override
    public void likeTrendClicked() {
        getLoadingFragment();
        String GUID = getGUID(ServiceConstant.LIKE_TREND);
        getFeatureStatesRequest(GUID,3);
    }

    @Override
    public void stalkCountCliked() {
        getLoadingFragment();
        String GUID = getGUID(ServiceConstant.STALKED_COUNT);
        getFeatureStatesRequest(GUID,4);
    }
}
