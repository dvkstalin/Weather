package com.assessment.weather.async;

import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * Created by Siddhartha on 8/2/2017.
 */

public interface CustomCallBack extends Callback<ResponseBody> {
      void serverErrorResponseHandling(int code, String source);
}
