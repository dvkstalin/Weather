package com.assessment.weather.async;


import com.assessment.weather.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Siddhartha on 6/21/2017.
 */
public class ApiClient {

    private static ApiClient mInstance;
    private static boolean weather = false;

    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .callFactory(new Call.Factory() {
                    @Override
                    public Call newCall(Request request) {
                        request = request.newBuilder().tag(new Object[]{null}).build();
                        Call call = getOkHttpClient().newCall(request);
                        ((Object[]) request.tag())[0] = call;
                        return call;
                    }
                })
                .build();
        return retrofit;
    }

    public OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//      OkHttpClient okHttpClient = builder.addInterceptor(interceptor).build();
        OkHttpClient okHttpClient = builder.connectTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        okHttpClient.newBuilder().dispatcher(dispatcher);

        return okHttpClient;
    }

    public ApiInterface getApiInterface() {
        ApiInterface apiInterface = getClient().create(ApiInterface.class);
        return apiInterface;
    }
}
