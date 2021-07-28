package com.assessment.weather;

import android.app.Application;
import android.provider.Settings;
import android.text.TextUtils;
import com.assessment.weather.async.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;
    public static String androidId;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this);
        //  Fabric.with(this, new Crashlytics());
        mInstance = this;
        androidId = Settings.Secure.getString(getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        // Initialize the Branch object
        // Branch logging for debugging
        //Branch.enableLogging();
        //Branch.getAutoInstance(this);

    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public <T> void enqueueRequestwithTag(Call<T> responseCall, Callback<T> callback, String tag) {
        //set the default tag if tag is empty
        ((Object[]) responseCall.request().tag())[0] = (TextUtils.isEmpty(tag) ? TAG : tag);
        responseCall.enqueue(callback);
    }

    public <T> void enqueueRequest(Call<T> responseCall, Callback<T> callback) {
        if (!responseCall.isExecuted()) {
            responseCall.enqueue(callback);

        }
    }

    public void cancelAllRequest() {
        ApiClient.getInstance().getOkHttpClient().dispatcher().cancelAll();
    }

    public List<okhttp3.Call> getAllQueuedCalls() {
        return ApiClient.getInstance().getOkHttpClient().dispatcher().queuedCalls();
    }
}
