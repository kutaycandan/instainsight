package com.kutaycandan.instainsight.service;

import com.kutaycandan.instainsight.model.request.GetStalkBalanceRequest;
import com.kutaycandan.instainsight.model.response.BaseResponse;
import com.kutaycandan.instainsight.model.response.ServiceUrlResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerUrlService {

    @GET("GetiraliClientEndpoint.asmx/getIITicket?VersionCode=testandroid")
    Call<ServiceUrlResponse> getUrl();
}
