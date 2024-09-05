package com.dimstyl.locationapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

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

        fun reverseGeocoderLocation(context: Context, location: Location): String {
            val geocoder = Geocoder(context, Locale.getDefault())
            val coordinates = LatLng(location.latitude, location.longitude)
            val address: MutableList<Address>? =
                geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)
            return address?.get(0)?.getAddressLine(0) ?: "Unknown"
        }
    }

}