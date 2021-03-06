package com.assessment.weather.utils;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Created by Bharath on 20-02-2017.
 */

public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\" +
            ".[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";

    // Error Messages
    public static final String REQUIRED_MSG = "required";
    public static final String PASSWORD_MATCH_MSG = "Password is not matched";
    public static final String EMAIL_MSG = "Invalid Email";
    private static final String PHONE_MSG = "###-#######";

    // call this method when you need to check email validation
    /*public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }*/

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, TextInputLayout textInputLayout) {
        if(editText.getText().toString().length()!=10)
        {
            textInputLayout.setError("Enter 10 digit mobile number");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // return true if the input field is valid, based on the parameter passed
   /* public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }*/

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText, TextInputLayout textInputLayout) {

        String text = editText.getText().toString().trim();
        //editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            textInputLayout.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }

    public static boolean isPasswordMatched(String password, String confirmPassword, TextInputLayout textInputLayout){
        boolean isValid=true;
        if(!password.isEmpty() && !confirmPassword.isEmpty()){
            if(!password.equals(confirmPassword)){
                isValid=false;
                textInputLayout.setError(PASSWORD_MATCH_MSG);
            }
        }
        return isValid;
    }
}
