package com.assessment.weather.async;

/**
 * Created by jpotts18 on 5/11/15.
 */
public interface OnRequestListener {

    void onRequestCompletion(int pid, String responseJson);

    void onRequestCompletionError(int pid, String error);
}
