package com.assessment.weather.async

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("v1/forecast.json")
    fun weatherDataRequest(@Query("key") key:String, @Query("q") q:String,@Query("days") days:String): Call<ResponseBody>

}