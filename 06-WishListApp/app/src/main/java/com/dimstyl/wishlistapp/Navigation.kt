package com.dimstyl.wishlistapp

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    viewModel: WishViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeView(navController, viewModel)
        }
        composable(Screen.AddWishScreen.route) {
            AddEditDetailView(viewModel = viewModel, navController = navController)
        }
        composable("${Screen.EditWishScreen.route}/{id}") {
            val id = it.arguments?.getString("id")?.toLongOrNull() ?: 0L

            if (id == 0L) {
                Toast.makeText(
                    navController.context,
                    "Something went wrong while retrieving the wish",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigateUp()
            }

            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}