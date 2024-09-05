package com.dimstyl.shoppinglist

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationUtil {

    companion object {
        fun hasLocationPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }

        @SuppressLint("MissingPermission")
        fun requestLocationUpdates(viewModel: LocationViewModel, context: Context) {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    locationResult.lastLocation?.let {
                        val location = Location(it.latitude, it.longitude)
                        viewModel.updateLocation(location)
                    }
                }
            }

            val locationRequest = LocationRequest
                .Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 2000)
                .build()

            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

}