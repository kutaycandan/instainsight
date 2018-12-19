package com.mirvinstalk.app.service;

import com.mirvinstalk.app.model.HdProfilePicDto;
import com.mirvinstalk.app.model.InstaUserModel;
import com.mirvinstalk.app.model.InstaUserModelFollowerCount;
import com.mirvinstalk.app.model.InstaUserModelLikeCount;
import com.mirvinstalk.app.model.InstaUserProfileData;
import com.mirvinstalk.app.model.LatestPopularPostDto;
import com.mirvinstalk.app.model.request.GetFeatureDataRequest;
import com.mirvinstalk.app.model.request.GetFeatureStatesRequest;
import com.mirvinstalk.app.model.request.GetStalkBalanceRequest;
import com.mirvinstalk.app.model.request.LoginByCookieRequest;
import com.mirvinstalk.app.model.request.OrderFeaturesRequest;
import com.mirvinstalk.app.model.request.RegisterRequest;
import com.mirvinstalk.app.model.request.StalkAmountRequest;
import com.mirvinstalk.app.model.response.ActivePurchasesResponse;
import com.mirvinstalk.app.model.response.BaseResponse;
import com.mirvinstalk.app.model.response.OrderFeaturesResponse;
import com.mirvinstalk.app.model.response.RegisterResponse;
import com.mirvinstalk.app.model.response.StalkAmountResponse;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InstaInsightService {

    @POST("Register")
    Call<BaseResponse<RegisterResponse>> registerRequest(@Body RegisterRequest request);

    @POST("LoginByCookie")
    Call<BaseResponse<RegisterResponse>> loginRequest(@Body LoginByCookieRequest request);

    @POST("GetActivePurchases")
    Call<BaseResponse<ActivePurchasesResponse>> getActivePurchases(@Body LoginByCookieRequest request);

    @POST("OrderFeatures")
    Call<BaseResponse<OrderFeaturesResponse>> orderFeaturesRequest(@Body OrderFeaturesRequest request);

    @POST("GetFeatureStates")
    Call<BaseResponse<ArrayList<ArrayList<String>>>> getFeatureStatesRequest(@Body GetFeatureStatesRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<InstaUserProfileData>>> getInstaUserProfileDataRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> getFeatureDataRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelFollowerCount>>>> getPopularFollowersRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<InstaUserModelLikeCount>>>> getLikeCountRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> getLikeTrendDataRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<Integer>>> getStalkedCountDataRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<LatestPopularPostDto>>>> getLatestPopularPostsDataRequest(@Body GetFeatureDataRequest request);

    @POST("GetFeatureData")
    Call<BaseResponse<BaseResponse<HdProfilePicDto>>> getHdProfilePicture(@Body GetFeatureDataRequest request);


    @POST("GetStalkBalance")
    Call<BaseResponse<Integer>> getStalkBalance(@Body GetStalkBalanceRequest request);

    @POST("StalkHandler")
    Call<BaseResponse<StalkAmountResponse>> postStalk(@Body StalkAmountRequest request);


}
