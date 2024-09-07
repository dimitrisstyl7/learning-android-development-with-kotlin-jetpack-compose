package com.dimstyl.wishlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dimstyl.wishlistapp.ui.theme.WishListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishListAppTheme {
                Navigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    WishListAppTheme {
        Navigation()
    }
}