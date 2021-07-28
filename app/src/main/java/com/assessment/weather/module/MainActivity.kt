package com.assessment.weather.module

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.weather.R
import com.assessment.weather.module.model.ForecastdayItem
import com.assessment.weather.module.model.WeatherDataResponseModel
import com.assessment.weather.utils.AppConstants
import com.assessment.weather.utils.NetworkStatus
import com.assessment.weather.utils.Utils
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), IMain.IMainView {
    lateinit var mPresenter: IMain.IMainPresenter
    var PERMISSION_ID = 44
    var REQUEST_CHECK_SETTINGS = 100
    var mFusedLocationClient: FusedLocationProviderClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        getLastLocation()
    }

    private fun initialize() {
        mPresenter = MainPresentorImpl(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun setUpAdapter(weatherDataResponseModel: WeatherDataResponseModel) {
        val forecastLinearLayout = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        forecastRecycler.layoutManager = forecastLinearLayout
        val mForecastListAdapter = ForecastListAdapter(this)
        forecastRecycler.adapter = mForecastListAdapter
        mForecastListAdapter.addAllCategory(weatherDataResponseModel.forecast!!.forecastday as ArrayList<ForecastdayItem>)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
        )
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun permissionSettingsDialog(title:String, message:String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton(R.string.open_setting,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    if(title==getString(R.string.permission_needed)){
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                                "package", this.packageName,
                                null
                        )
                        intent.data = uri
                        startActivity(intent)
                    }else{
                        startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                })
        alertDialogBuilder.setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                    mPresenter.getWeatherData("Bangalore")
                })
        val dialog = alertDialogBuilder.create()
        dialog.show()
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                progressBar.visibility = View.VISIBLE
                mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        if (NetworkStatus.checkNetworkStatus(this)) {
                            getAddress(
                                    location.latitude,
                                    location.longitude
                            )
                        }
                    }
                }
            } else {
                enableLocation()
            }
        } else {
            requestPermissions()
        }
    }

    private fun enableLocation() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 30 * 1000
        mLocationRequest.smallestDisplacement = 10f
        mLocationRequest.fastestInterval = 5 * 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val task: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
        task.addOnCompleteListener { task ->
            try {
                val response =
                    task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                    }

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->                                     // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable: ResolvableApiException =
                                    exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    this,
                                    REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = TimeUnit.SECONDS.toMillis(60)
        mLocationRequest.fastestInterval = TimeUnit.SECONDS.toMillis(30)
        mLocationRequest.maxWaitTime = TimeUnit.MINUTES.toMillis(2)
        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            if (NetworkStatus.checkNetworkStatus(this@MainActivity)) {
                getAddress(
                        mLastLocation.latitude,
                        mLastLocation.longitude
                )
            }
        }
    }

    private fun getAddress(lat2: Double, lng2: Double) {
        var addresses: List<Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        var city: String? = null
        try {
            addresses = geocoder.getFromLocation(
                    lat2,
                    lng2,
                    1
            )
            city = if(addresses!![0].locality!=null){
                addresses[0].locality
            }else{
                addresses[0].subAdminArea
            }
            mPresenter.getWeatherData(city)
        } catch (e: IOException) {
            getLastLocation()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onWeatherDataRequestSuccess(weatherDataResponseModel: WeatherDataResponseModel) {
        cityTv.text = weatherDataResponseModel.location!!.name
        descriptionTv.text = weatherDataResponseModel.current!!.condition!!.text
        tempTv.text = "${weatherDataResponseModel.current.tempC.toString().split(".")[0]}°"
        windSpeedTv.text = "Wind Speed: ${weatherDataResponseModel.current.windKph.toString()} kph"
        setUpAdapter(weatherDataResponseModel)
        progressBar.visibility = View.GONE
    }

    override fun onWeatherDataRequestError(error: String) {
        Utils.showToast(this, error)
        progressBar.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }else{
                permissionSettingsDialog(getString(R.string.permission_needed),getString(R.string.location_permission_description))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if(RESULT_OK == resultCode){
                getLastLocation()
            }else{
                permissionSettingsDialog(getString(R.string.location_needed),getString(R.string.location_permission_description))
            }
        }
    }
}