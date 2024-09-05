package com.dimstyl.shoppinglist

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface GeocodingApiService {

    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latLng: String,
        @Query("key") apiKey: String = BuildConfig.MAPS_API_KEY,
        @Header("X-Android-Package") androidPackage: String = BuildConfig.APPLICATION_ID,
        @Header("X-Android-Cert") certFingerprint: String = BuildConfig.SHA_1_FINGERPRINT
    ): GeocodingResponse

}