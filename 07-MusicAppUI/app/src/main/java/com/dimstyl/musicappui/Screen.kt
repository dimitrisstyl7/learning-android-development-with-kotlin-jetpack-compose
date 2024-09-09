package com.dimstyl.musicappui

import androidx.annotation.DrawableRes

sealed class Screen(val title: String, val route: String) {

    sealed class DrawerNavScreen(title: String, route: String, @DrawableRes val icon: Int) :
        Screen(title, route) {
        data object Account :
            DrawerNavScreen("Account", "account", R.drawable.baseline_account_box_24)

        data object Subscription :
            DrawerNavScreen("Subscription", "subscription", R.drawable.baseline_library_music_24)

        data object AddAccount :
            DrawerNavScreen("Add Account", "add_account", R.drawable.baseline_person_add_alt_1_24)
    }

    sealed class BottomNavScreen(title: String, route: String, @DrawableRes val icon: Int) :
        Screen(title, route) {
        data object Home : BottomNavScreen("Home", "home", R.drawable.baseline_home_24)

        data object Browse : BottomNavScreen("Browse", "browse", R.drawable.baseline_grid_view_24)

        data object Library :
            BottomNavScreen("Library", "library", R.drawable.baseline_library_music_24)
    }

}

val drawerNavScreens = listOf(
    Screen.DrawerNavScreen.Account,
    Screen.DrawerNavScreen.Subscription,
    Screen.DrawerNavScreen.AddAccount
)

val bottomNavScreens = listOf(
    Screen.BottomNavScreen.Home,
    Screen.BottomNavScreen.Browse,
    Screen.BottomNavScreen.Library
)