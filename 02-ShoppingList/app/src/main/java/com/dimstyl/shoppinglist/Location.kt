package com.dimstyl.shoppinglist

data class Location(val latitude: Double, val longitude: Double) {
    override fun toString() = "$latitude,$longitude"
}

data class GeocodingResponse(val results: List<GeocodingResult>, val status: String)

data class GeocodingResult(val formatted_address: String)