package com.assessment.weather.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/*
import com.google.android.gms.maps.model.BitmapDescriptorFactory;*/

//import com.google.android.gms.maps.model.BitmapDescriptor;

public class Utils {

    public interface dialogInterface {
        public void dialogClick();
    }

    public static boolean isFilePresent(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName + ".pdf");
        return file.exists();
    }

    public static String tabName = "featured";
    Activity activity;
    public static ProgressDialog progress;
    public static Bitmap bitmap = null;
    public static AlertDialog mAlertDialog;
    public static AlertDialog.Builder malertBuilder;
    public static TextView mAlertDialogOkTV, mAAlertDialogCancelTV;

    public static void hideToolbar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

    }

    public static String getSubString(String mainString, String startString, String lastString) {

        try {
            String endString = "";
            int endIndex = mainString.indexOf(lastString);
            int startIndex = mainString.indexOf(startString);
            Log.d("message", "" + mainString.substring(startIndex, endIndex));
            endString = mainString.substring(startIndex, endIndex);
            return endString;
        } catch (Exception e) {
            return "";
        }
    }

    public static String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage =(((double)currentSeconds)/totalSeconds)*100;

        // return percentage
        return percentage.intValue();
    }

    public static int progressToTimer(int progress, long totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static int getDaysDifference(String dateBeforeString, String dateAfterString) {

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        float daysBetween = 0.0f;
        System.out.println(dateBeforeString + "--||--" + dateAfterString);
        try {
            Date dateBefore = myFormat.parse(dateBeforeString.replace("/", " "));
            Date dateAfter = myFormat.parse(dateAfterString.replace("/", " "));
            long difference = dateAfter.getTime() - dateBefore.getTime();
            daysBetween = (difference / (1000 * 60 * 60 * 24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
            System.out.println("Number of Days between dates: " + daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Math.round(daysBetween);
    }

    public static void showProgress(Context context) {
        try {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        } catch (Exception e) {

        }
    }

    public static void showProgress(Context context, String message) {
        try {
            progress = new ProgressDialog(context);
            progress.setMessage(message);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.show();
        } catch (Exception e) {

        }
    }

    public static void stopProgress(Context context) {
        try {
            if (progress.isShowing())
                progress.dismiss();
        } catch (Exception e) {

        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /*
     * Encodes the form the url using namevaluepair & base url
     */
     /* public static String getEncodedUrl(String baseurl, List<BasicNameValuePair> nameValuePairs) {

            if (nameValuePairs != null) {
                  String paramString = URLEncodedUtils.format(nameValuePairs,
                          "utf-8");
                  baseurl += "?" + paramString;
                  Log.e("--Encoded URL--", "-->>" + baseurl.toString());
            }
            return baseurl;
      }
*/
    /*
     * display Toast message
     */

    public static void showToast(Context context, String str) {
        if(context!=null){
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isStatusTrue(JSONObject jsonObject) {
        Boolean aStatus = false;
        if (jsonObject != null) {
            try {
                if (jsonObject.has(/*AppConstants.TAGNAME.STATUS.getValue()*/"status")) {
                    aStatus = jsonObject.getBoolean(/*AppConstants.TAGNAME.STATUS.getValue()
                     */"status");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aStatus;
    }

    /**
     * Check particular node is array.
     *
     * @param jsonObject
     * @return true if particular node is Array.
     */
    public static boolean isJsonArray(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONArray(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is object.
     *
     * @param jsonObject
     * @return true if particular node is Object.
     */
    public static boolean isJsonObject(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONObject(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is having particular key.
     *
     * @param jsonObject
     * @return true if particular node is having key.
     */
    public static boolean isJsonKeyAvailable(JSONObject jsonObject, String key) {

        return jsonObject.has(key);

    }

    public static void putStringinPrefs(Context mContext, String mKey,
                                        String mVal) {
        SharedPreferences.Editor prefsEditorr = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        try {
            prefsEditorr.putString(mKey, mVal.toString());
            prefsEditorr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Create image and save in file system.
     */
    public static File createImage(Context context, int height, int width,
                                   View view, String fileName) {
        Bitmap bitmapCategory = getBitmapFromView(view, height, width);
        return createFile(context, bitmapCategory, fileName);
    }

    /*
     * save bitmap image in phone memory
     */
    public static File createFile(Context context, Bitmap bitmap,
                                  String fileName) {

        File externalStorage = Environment.getExternalStorageDirectory();
        String sdcardPath = externalStorage.getAbsolutePath();
        File reportImageFile = new File(sdcardPath + "/YourFolderName"
                + fileName + ".jpg");
        try {
            if (reportImageFile.isFile()) {
                reportImageFile.delete();
            }
            if (reportImageFile.createNewFile()) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileOutputStream fo = new FileOutputStream(reportImageFile);
                fo.write(bytes.toByteArray());
                bytes.close();
                fo.close();
                return reportImageFile;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Unable to create Image.Try again",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /*
     * Take view screen shots
     */
    public static Bitmap getBitmapFromView(View view, int totalHeight,
                                           int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);

        view.measure(
                MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY));
        view.layout(0, 0, totalWidth, totalHeight);
        view.draw(canvas);
        return returnedBitmap;
    }

/*    public static boolean isLocationEnabled(Context context) {

        final LocationManager manager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        return manager != null
               && manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static String getDeviceIMEINo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }*/

    /*
     * This function copy InputStream bytes to OutputStream bytes
     *
     * @param InputStream
     *
     * @param OutputStream
     */
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    /**
     * this method is used to navigate the user to the native sharing apps on
     * the device so that user can shared the data on the various social sites
     * success : opens the sharing apps list on the device
     */

    public static void navigateToSharedScreen(Context context, String title,
                                              String shareText, String link, Uri url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
//		intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + shareText + " " + link);
        intent.putExtra(Intent.EXTRA_STREAM, url);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert dp to px and vice-versa
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * This method is used to convert the image into blurred bitmap image
     */
    public static Bitmap getBlurImage(Context context, Bitmap image) {
        /* Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.mipmap.home_info);
         * Bitmap blurredBitmap = Utils.getBlurImage(this, Icon);
         * mLogoImg.setBackgroundDrawable(new BitmapDrawable(getResources(), blurredBitmap));
         */

        float BITMAP_SCALE = 0.25f;
        float BLUR_RADIUS = 9.5f;

        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                RenderScript rs = RenderScript.create(context);
                ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
                Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
                theIntrinsic.setRadius(BLUR_RADIUS);
                theIntrinsic.setInput(tmpIn);
                theIntrinsic.forEach(tmpOut);
                tmpOut.copyTo(outputBitmap);
            }
        } catch (Exception e) {

        }

        return outputBitmap;
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static Bitmap getBitmap() {

        return bitmap;
    }

    public static void setBitMap(Bitmap bitmap) {

        Utils.bitmap = bitmap;
    }

  /*  @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/"
                                                                                         +
                                                                                         fontName));
    }*/

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("GMT");
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy, HH:mm");
            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
  /*  //Drop pin animation google map
    public static void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1800;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                                          / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }*/

    /*Convert a Drawable object to a Bitmap object*/
    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config
                .ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }

    /* convert between real pixel (px) and device indipenden pixel (dp, dip)*/
    private float px2Dp(float px, Context ctx) {
        return px / ctx.getResources().getDisplayMetrics().density;
    }

    private float dp2Px(float dp, Context ctx) {
        return dp * ctx.getResources().getDisplayMetrics().density;
    }

    /*
     * API 23 default component permissions
     */
    public static boolean checkPermission(Context context, String callPhone) {
        int result = ActivityCompat.checkSelfPermission(context, callPhone);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void makeShareIntent(Context context, String url, String title) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(Intent.createChooser(share, title));
    }

    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

    }

    /*  public static void checkingComponentPermission(final Context context, String permission) {

            PermissionListener permissionlistener = new PermissionListener() {
                  @Override
                  public void onPermissionGranted() {
                        Toast.makeText(context, "Permission Allowed", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, "Permission Denied\n" + deniedPermissions.toString(),
                                Toast.LENGTH_SHORT).show();
                  }
            };

            new TedPermission(context)
                    .setPermissionListener(permissionlistener)
                    .setPermissions(permission)
                    .check();

      }*/

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.v("base64", "" + imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void showLog(String tag, String msg) {
        if (AppConstants.isDebugBuild) {
            Log.v(tag, msg);
        }
    }

    public static String getCurrentTimestamp() {
        Long tsLong = System.currentTimeMillis()/* / 1000*/;
        Utils.showLog("getCurrentTimestamp", "-->>" + tsLong.toString());
        return tsLong.toString();
    }

    /*  public static BitmapDescriptor getBitmapIconFromDrawable(Drawable drawable) {
            Canvas canvas = new Canvas();
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config
                    .ARGB_8888);
            canvas.setBitmap(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
      }*/


 /*   private void getDelayTimerPopup(Activity context) {
        //  final int[] i = {0};
        android.support.v7.app.AlertDialog dialog;
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder
                (context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_settings, null);
        builder.setView(dialogView);
        dialog = builder.create();

        dialog.setCancelable(false);
        dialog.show();
    }*/

    /*  public static View showNewDialog(Context context) {
          malertBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style
                  .Theme_DeviceDefault_Light_Dialog));
          mAlertDialog = malertBuilder.create();
          LayoutInflater factory = LayoutInflater.from(context);
          final View view = factory.inflate(R.layout.activity_settings, null);
          mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          mAlertDialog.setView(view);
          mAlertDialog.show();
          return view;
      }*/
    public static String getCompleteAddress(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loct address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loct address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loct address", "Canont get Address!");
        }
        return strAdd;
    }
}
