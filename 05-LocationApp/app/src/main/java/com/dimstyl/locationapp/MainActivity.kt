package com.dimstyl.locationapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dimstyl.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: LocationViewModel = viewModel()
            LocationAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocationApp(viewModel)
                }
            }
        }
    }

}

@Composable
fun LocationApp(viewModel: LocationViewModel) {
    val context = LocalContext.current
    LocationDisplay(context, viewModel)
}


@Composable
fun LocationDisplay(context: Context, viewModel: LocationViewModel) {
    val location = viewModel.location.value
    val address = location?.let { LocationUtil.reverseGeocoderLocation(context, it) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                LocationUtil.requestLocationUpdates(viewModel, context)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(context, "Location permission is required", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "To enable location, go to settings and enable location",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (location != null) {
            Text("Latitude: ${location.latitude}")
            Text("Longitude: ${location.longitude}")
            Text("Address: $address")
        } else {
            Text("Location not available")
        }

        Button(onClick = {
            if (LocationUtil.hasLocationPermission(context)) {
                LocationUtil.requestLocationUpdates(viewModel, context)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }) {
            Text("Get Location")
        }
    }
}