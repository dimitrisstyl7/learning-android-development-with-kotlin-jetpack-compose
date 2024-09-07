package com.dimstyl.wishlistapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dimstyl.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavController, viewModel: WishViewModel) {
    Scaffold(
        topBar = {
            TopBarView("WishList") {}
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddWishScreen.route)
                },
                modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                containerColor = Color.Black
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
            }
        }
    ) { innerPadding ->
        val wishes = viewModel.getAllWishes.collectAsState(initial = emptyList())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(items = wishes.value, key = { wish -> wish.id }) { wish ->
                val dismissState = rememberDismissState {
                    if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                        viewModel.deleteWish(wish)
                    }
                    true
                }

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            targetValue = if (dismissState.dismissDirection in setOf(
                                    DismissDirection.StartToEnd,
                                    DismissDirection.EndToStart
                                )
                            ) Color.Red else Color.Transparent,
                            label = "Swipe to dismiss background color"
                        )
                        val alignment =
                            if (dismissState.dismissDirection == DismissDirection.StartToEnd) Alignment.CenterStart
                            else Alignment.CenterEnd

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    dismissContent = {
                        WishItem(wish) {
                            navController.navigate("${Screen.EditWishScreen.route}/${wish.id}")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}
