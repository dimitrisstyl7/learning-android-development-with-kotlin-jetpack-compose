package com.dimstyl.locationapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    fun updateLocation(location: Location) {
        _location.value = location
    }

}