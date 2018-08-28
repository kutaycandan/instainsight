package com.kutaycandan.instainsight.service;

import android.app.Activity;
import android.content.Context;

import com.kutaycandan.instainsight.constants.SharedPrefsConstant;
import com.kutaycandan.instainsight.util.SharedPrefsHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    private static InstaInsightService instance;
    private static ServerUrlService serverInstance;
    static String SERVER_URL = "http://www.dptmerkezi.com/";
    static String INSTA_INSIGHT_BASE_URL = "http://35.204.27.13/api/Values/";

    public static InstaInsightService getInstance(final Activity context) {
        if(SharedPrefsHelper.getInstance().get(SharedPrefsConstant.STRING_URL)!= null){
            INSTA_INSIGHT_BASE_URL = SharedPrefsHelper.getInstance().get(SharedPrefsConstant.STRING_URL);
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.addInterceptor(new ChuckInterceptor(context));
        httpClient.addInterceptor(httpLoggingInterceptor);

        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(INSTA_INSIGHT_BASE_URL)
                .client(client)
                .build();
        instance = retrofit.create(InstaInsightService.class);

        return instance;
    }

    public static ServerUrlService getServerInstance(final Context context) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.addInterceptor(new ChuckInterceptor(context));
        httpClient.addInterceptor(httpLoggingInterceptor);

        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .client(client)
                .build();
        serverInstance = retrofit.create(ServerUrlService.class);

        return serverInstance;
    }


}