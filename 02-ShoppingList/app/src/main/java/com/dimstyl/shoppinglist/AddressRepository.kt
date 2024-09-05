package com.dimstyl.shoppinglist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressRepository {

    private val apiService: GeocodingApiService = RetrofitClient.create()

    suspend fun fetchAddress(latLng: String): Result<GeocodingResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAddressFromCoordinates(latLng)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}