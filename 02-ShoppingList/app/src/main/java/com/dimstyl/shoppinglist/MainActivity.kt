package com.dimstyl.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.dimstyl.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "shoppingListScreen") {
        composable("shoppingListScreen") {
            val addresses = viewModel.addresses.value

            ShoppingListApp(
                viewModel = viewModel,
                navController = navController,
                context = context,
                address = if (addresses.isEmpty()) "No address found" else addresses[0].formatted_address
            )
        }

        dialog("locationScreen") {
            viewModel.location.value?.let { location ->
                LocationSelectionScreen(location = location) {
                    viewModel.fetchAddress(it.toString())
                    navController.popBackStack()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShoppingListPreview() {
    ShoppingListTheme {
        Navigation()
    }

}