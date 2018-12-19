package com.mirvinstalk.app.service;

import com.mirvinstalk.app.model.request.GetStalkBalanceRequest;
import com.mirvinstalk.app.model.response.BaseResponse;
import com.mirvinstalk.app.model.response.ServiceUrlResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerUrlService {

    @GET("GetiraliClientEndpoint.asmx/getIITicket?VersionCode=testandroid")
    Call<ServiceUrlResponse> getUrl();
}
