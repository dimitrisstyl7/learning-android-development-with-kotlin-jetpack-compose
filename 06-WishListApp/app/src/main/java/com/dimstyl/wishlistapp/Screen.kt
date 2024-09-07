package com.dimstyl.wishlistapp

sealed class Screen(val route: String) {

    data object HomeScreen : Screen("home")
    data object AddWishScreen : Screen("add_wish")
    data object EditWishScreen : Screen("edit_wish")
}