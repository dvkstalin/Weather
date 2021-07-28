package com.assessment.weather.module

import android.content.pm.PackageManager
import android.util.Log
import com.assessment.weather.async.ApiClient
import com.assessment.weather.async.AsyncInteractor
import com.assessment.weather.async.OnRequestListener
import com.assessment.weather.utils.AppConstants
import com.google.gson.Gson
import com.assessment.weather.module.model.WeatherDataResponseModel


class MainPresentorImpl(private var mView: IMain.IMainView) :
    IMain.IMainPresenter, OnRequestListener {
    private var mAsync: AsyncInteractor
    private val TAG = MainPresentorImpl::class.java.simpleName

    init {
        Log.v(TAG, "inside init")
        mAsync = AsyncInteractor()
    }

    override fun getWeatherData(city:String) {
        mAsync.retrofitJsonServerCall(
            AppConstants.PID_WEATHER_DATA, this,
            ApiClient.getInstance().apiInterface.weatherDataRequest("709bf49436f643aa978103404212707",city,"4"))
    }

    override fun onRequestCompletion(pid: Int, responseJson: String?) {
        when(pid)
        {
            AppConstants.PID_WEATHER_DATA->
            {
                val gson = Gson()
                val mWeatherDataResponseModel = gson.fromJson(responseJson?.trim(),
                    WeatherDataResponseModel::class.java)
                mView.onWeatherDataRequestSuccess(mWeatherDataResponseModel)
            }
        }
    }

    override fun onRequestCompletionError(pid: Int, error: String?) {
        when(pid)
        {
            AppConstants.PID_WEATHER_DATA->
            {
                //val mJsonObject = JSONObject(error!!)
                mView.onWeatherDataRequestError(error!!)
            }
        }
    }
}
