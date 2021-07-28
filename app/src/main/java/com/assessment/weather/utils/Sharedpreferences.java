package com.assessment.weather.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Sharedpreferences {

    private Context context;
    private SharedPreferences pref;
    private static Editor editor;
    private static final String PREF_NAME = "com.ineduz.careermap.sp";
    private int PRIVATE_MODE = 0;

    @SuppressLint("StaticFieldLeak")
    private static Sharedpreferences userData = null;

    private static final String TAG_USER_TYPE = "sp_user_type";
    private static final String TAG_USER_ID = "sp_user_id";
    private static final String TAG_USER_MOBILE_NO = "sp_user_mobile_no";
    private static final String TAG_LOGGED_IN_USER_SOCIAL_ID = "sp_logged_in_user_social_id";
    private static final String TAG_USER_NAME = "sp_user_name";
    private static final String TAG_USER_PROFILE_PIC = "sp_user_profile_pic";
    private static final String TAG_USER_PROFILE_PIC_name = "sp_user_profile_pic_name";
    private static final String TAG_HAS_USER_LOGGEDIN = "sp_has_user_loggedin";
    private static final String TAG_LOGGEDIN_USER_TOKEN = "sp_loggedin_user_token";
    private static final String TAG_LOGGEDIN_FCM_TOKEN = "loggedin_fcm_token";
    private static final String TAG_IS_DARKMODE = "sp_is_darkMode";
    private static final String TAG_SELECTED_HOUSE_NO = "sp_selected_house_no";
    private static final String TAG_SELECTED_ADDRESS_STREET = "sp_selected_address_street";
    private static final String TAG_RECENT_SEARCH_LIST_ARRAY = "sp_recent_search_list_array";
    private static final String TAG_FM_LIST_ARRAY = "sp_fm_list_array";
    private static final String TAG_FAV_FM_LIST_ARRAY = "sp_fav_fm_list_array";
    private static final String TAG_RECENT_LIST = "sp_recent_list";
    private static final String TAG_LANG = "sp_lang";
    private static final String TAG_LOCATION = "sp_location";

    private Sharedpreferences(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static Sharedpreferences getInstance(Context c) {
        if (userData == null) {
            userData = new Sharedpreferences(c);
        }
        return userData;
    }

    public String getLoggedInUserToken() {
        return pref.getString(TAG_LOGGEDIN_USER_TOKEN, "");
    }

    public void setLoggedInUserToken(String status) {
        try {
            editor.putString(TAG_LOGGEDIN_USER_TOKEN, status);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getLoggedInFcmToken() {
        return pref.getString(TAG_LOGGEDIN_FCM_TOKEN, "");
    }

    public void setLoggedInFcmToken(String status) {
        try {
            editor.putString(TAG_LOGGEDIN_FCM_TOKEN, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getRecentList() {
        return pref.getString(TAG_RECENT_LIST, "");
    }

    public void setRecentList(String status) {
        try {
            editor.putString(TAG_RECENT_LIST, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public int getLoggedInUserType() {
        return pref.getInt(TAG_USER_TYPE, 1);
    }

    public void setLoggedInUserType(int status) {
        try {
            editor.putInt(TAG_USER_TYPE, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserId() {
        return pref.getString(TAG_USER_ID, "");
    }

    public void setLoggedInUserId(String status) {
        try {
            editor.putString(TAG_USER_ID, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserProfileImage() {
        return pref.getString(TAG_USER_PROFILE_PIC, "");
    }

    public void setLoggedInUserProfileImage(String status) {
        try {
            editor.putString(TAG_USER_PROFILE_PIC, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserProfilePicName() {
        return pref.getString(TAG_USER_PROFILE_PIC_name, "A");
    }

    public void setLoggedInUserProfilePicName(String status) {
        try {
            editor.putString(TAG_USER_PROFILE_PIC_name, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserName() {
        return pref.getString(TAG_USER_NAME, "");
    }

    public void setLoggedInUserName(String status) {
        try {
            editor.putString(TAG_USER_NAME, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserMobileNo() {
        return pref.getString(TAG_USER_MOBILE_NO, "");
    }

    public void setLoggedInUserMobileNo(String status) {
        try {
            editor.putString(TAG_USER_MOBILE_NO, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserHouseNo() {
        return pref.getString(TAG_SELECTED_HOUSE_NO, "");
    }

    public void setLoggedInUserHouseNo(String status) {
        try {
            editor.putString(TAG_SELECTED_HOUSE_NO, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserLocation() {
        return pref.getString(TAG_LOCATION, "");
    }

    public void setLoggedInUserLocation(String status) {
        try {
            editor.putString(TAG_LOCATION, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserAddressStreet() {
        return pref.getString(TAG_SELECTED_ADDRESS_STREET, "");
    }

    public void setLoggedInUserAddressStreet(String status) {
        try {
            editor.putString(TAG_SELECTED_ADDRESS_STREET, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getFMListArray() {
        return pref.getString(TAG_FM_LIST_ARRAY, "");
    }

    public void setFMListArray(String status) {
        try {
            editor.putString(TAG_FM_LIST_ARRAY, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getFavFMListArray() {
        return pref.getString(TAG_FAV_FM_LIST_ARRAY, "");
    }

    public void setFavFMListArray(String status) {
        try {
            editor.putString(TAG_FAV_FM_LIST_ARRAY, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getRecentSearchArray() {
        return pref.getString(TAG_RECENT_SEARCH_LIST_ARRAY, "");
    }

    public void setRecentSearchArray(String status) {
        try {
            editor.putString(TAG_RECENT_SEARCH_LIST_ARRAY, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserLang() {
        return pref.getString(TAG_LANG, "");
    }

    public void setLoggedInUserLang(String status) {
        try {
            editor.putString(TAG_LANG, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getLoggedInUserSocialId() {
        return pref.getString(TAG_LOGGED_IN_USER_SOCIAL_ID, "");
    }

    public void setLoggedInUserSocialId(String status) {
        try {
            editor.putString(TAG_LOGGED_IN_USER_SOCIAL_ID, status);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public Boolean getHasUserLoggedIn() {
        return pref.getBoolean(TAG_HAS_USER_LOGGEDIN, false);
    }

    public void setHasUserLoggedIn(Boolean status) {
        try {
            editor.putBoolean(TAG_HAS_USER_LOGGEDIN, status);
            editor.commit();
        } catch (Exception e) {
        }
    }
}
