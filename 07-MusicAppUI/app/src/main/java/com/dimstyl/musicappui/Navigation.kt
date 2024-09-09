package com.dimstyl.musicappui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dimstyl.musicappui.ui.screens.account.AccountScreen
import com.dimstyl.musicappui.ui.screens.browse.BrowseScreen
import com.dimstyl.musicappui.ui.screens.home.HomeScreen
import com.dimstyl.musicappui.ui.screens.library.LibraryScreen
import com.dimstyl.musicappui.ui.screens.subscription.SubscriptionScreen

@Composable
fun Navigation(navController: NavController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomNavScreen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.DrawerNavScreen.Account.route) {
            AccountScreen()
        }

        composable(Screen.DrawerNavScreen.Subscription.route) {
            SubscriptionScreen()
        }

        composable(Screen.BottomNavScreen.Home.route) {
            HomeScreen()
        }

        composable(Screen.BottomNavScreen.Browse.route) {
            BrowseScreen()
        }

        composable(Screen.BottomNavScreen.Library.route) {
            LibraryScreen()
        }
    }
}