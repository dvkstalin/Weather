package com.assessment.weather.utils;

import com.assessment.weather.BuildConfig;

public class AppConstants {

    public static boolean isLogEnabled = true;
    public static boolean isDebugBuild = true;

    public static String BASEURL;
    public static String WEBSERVICE_HOST;

    public static final int PID_WEATHER_DATA = 9892;

    static {
        if (BuildConfig.DEBUG) {
            WEBSERVICE_HOST = "http://api.weatherapi.com/"; //Local Ip
        } else {
            WEBSERVICE_HOST = "http://api.weatherapi.com/"; //Live Ip
        }
        BASEURL = WEBSERVICE_HOST;
    }


}
