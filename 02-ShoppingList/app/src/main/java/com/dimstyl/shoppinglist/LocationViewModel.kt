package com.dimstyl.shoppinglist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val repository = AddressRepository()
    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    private val _addresses = mutableStateOf(listOf<GeocodingResult>())
    val addresses: State<List<GeocodingResult>> = _addresses


    fun updateLocation(newLocation: Location) {
        _location.value = newLocation
    }

    fun fetchAddress(latLng: String) {
        viewModelScope.launch {
            val result = repository.fetchAddress(latLng);

            if (result.isSuccess) {
                _addresses.value = result.getOrNull()?.results ?: emptyList()
            } else {
                Log.e(
                    "LocationViewModel",
                    "Error fetching address: ${result.exceptionOrNull()?.message}"
                )
                _addresses.value = emptyList()
            }
        }
    }

}