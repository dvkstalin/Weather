package com.assessment.weather.module

import com.assessment.weather.module.model.WeatherDataResponseModel

interface IMain {
    interface IMainPresenter{
        fun getWeatherData(city:String)
    }
    interface IMainView{
        fun onWeatherDataRequestSuccess(weatherDataResponseModel: WeatherDataResponseModel)
        fun onWeatherDataRequestError(error:String)
    }
}