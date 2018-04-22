package com.kutaycandan.instainsight.service;

import com.kutaycandan.instainsight.model.InstaUserModel;
import com.kutaycandan.instainsight.model.InstaUserProfileData;
import com.kutaycandan.instainsight.model.request.GetFeatureDataRequest;
import com.kutaycandan.instainsight.model.request.GetFeatureStatesRequest;
import com.kutaycandan.instainsight.model.request.LoginByCookieRequest;
import com.kutaycandan.instainsight.model.request.OrderFeaturesRequest;
import com.kutaycandan.instainsight.model.request.RegisterRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.OrderFeaturesResponse;
import com.kutaycandan.instainsight.model.response.RegisterResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InstaInsightService {

    @POST("api/Values/Register")
    Call<BaseResponse<RegisterResponse>> registerRequest(@Body RegisterRequest request);

    @POST("api/Values/LoginByCookie")
    Call<BaseResponse<RegisterResponse>> loginRequest(@Body LoginByCookieRequest request);

    @POST("api/Values/OrderFeatures")
    Call<BaseResponse<OrderFeaturesResponse>> orderFeaturesRequest(@Body OrderFeaturesRequest request);

    @POST("api/Values/GetFeatureStates")
    Call<BaseResponse<ArrayList<String>>> getFeatureStatesRequest(@Body GetFeatureStatesRequest request);

    @POST("api/Values/GetFeatureData")
    Call<BaseResponse<BaseResponse<InstaUserProfileData>>> getInstaUserProfileDataRequest(@Body GetFeatureDataRequest request);

    @POST("api/Values/GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<InstaUserModel>>>> getFeatureDataRequest(@Body GetFeatureDataRequest request);

    @POST("api/Values/GetFeatureData")
    Call<BaseResponse<BaseResponse<ArrayList<Integer>>>> getLikeTrendDataRequest(@Body GetFeatureDataRequest request);

    @POST("api/Values/GetFeatureData")
    Call<BaseResponse<BaseResponse<Integer>>> getStalkedCountDataRequest(@Body GetFeatureDataRequest request);


}
