package com.kutaycandan.instainsight.service;

import com.kutaycandan.instainsight.model.HdProfilePicDto;
import com.kutaycandan.instainsight.model.InstaUserModel;
import com.kutaycandan.instainsight.model.InstaUserModelFollowerCount;
import com.kutaycandan.instainsight.model.InstaUserModelLikeCount;
import com.kutaycandan.instainsight.model.InstaUserProfileData;
import com.kutaycandan.instainsight.model.LatestPopularPostDto;
import com.kutaycandan.instainsight.model.request.GetFeatureDataRequest;
import com.kutaycandan.instainsight.model.request.GetFeatureStatesRequest;
import com.kutaycandan.instainsight.model.request.GetStalkBalanceRequest;
import com.kutaycandan.instainsight.model.request.LoginByCookieRequest;
import com.kutaycandan.instainsight.model.request.OrderFeaturesRequest;
import com.kutaycandan.instainsight.model.request.RegisterRequest;
import com.kutaycandan.instainsight.model.request.StalkAmountRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.OrderFeaturesResponse;
import com.kutaycandan.instainsight.model.response.RegisterResponse;
import com.kutaycandan.instainsight.model.response.StalkAmountResponse;

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
