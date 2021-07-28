package com.assessment.weather.async;

import android.os.Handler;
import android.util.Log;

import com.assessment.weather.AppController;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AsyncInteractor implements IAsyncInteractor {

    public void validateCredentialsAsyncJsonRequest(final OnRequestListener listener, final int pid, final
    Call<ResponseBody> responseBodyCall) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                retrofitJsonServerCall(pid, listener, responseBodyCall);
            }
        }, 2000);
    }

    public void retrofitJsonServerCall(final int pid, final OnRequestListener listener, final Call<ResponseBody>
            responseBodyCall) {
        CustomCallBack customCallBack = new CustomCallBack() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    try {
                        Log.v("response","response1-->");
                        listener.onRequestCompletion(pid, response.body().string());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Log.v("response","responsetfyhfghfgh2-->");
                        listener.onRequestCompletionError(pid, response.message().trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("response","responsefghfghfghfg3-->");
                        listener.onRequestCompletionError(pid, response.message().trim());
                    }
                } else {
                    try {
                        Log.v("response","responsghjghjghjghje4-->");
                        serverErrorResponseHandling(response.code(), response.errorBody().string().trim());
                        // listener.onRequestCompletionError(pid, response.errorBody().string().trim());
                    } catch (IOException e) {
                        Log.v("response","responseghjghjghjghj5-->");
                        listener.onRequestCompletionError(pid, response.message().trim());
                    }
                }
            }

            @Override
            public void serverErrorResponseHandling(int code, String source) {
                Log.v("response","responseghjghjghjghj7-->");
                listener.onRequestCompletionError(pid, source.trim());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    t.printStackTrace();
                    listener.onRequestCompletionError(pid, "Please check the server");
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onRequestCompletionError(pid, "errormsg");
                }
            }
        };
        AppController.getInstance().enqueueRequest(responseBodyCall, customCallBack);

    }
}
