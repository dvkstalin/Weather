package com.assessment.weather.async;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by jpotts18 on 5/11/15.
 */
public interface IAsyncInteractor {

    void validateCredentialsAsyncJsonRequest(final OnRequestListener listener, final int pid, final Call<ResponseBody>
            responseBodyCall);
}
