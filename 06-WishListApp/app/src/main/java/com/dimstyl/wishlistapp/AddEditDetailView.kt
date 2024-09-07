package com.dimstyl.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dimstyl.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(id: Long = 0L, viewModel: WishViewModel, navController: NavController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    if (id != 0L) {
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish())
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBarView(title = if (id != 0L) "Edit Wish" else "Add Wish") {
                navController.navigateUp()
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState
            ) { viewModel.onWishTitleChange(it) }

            Spacer(Modifier.height(10.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState
            ) { viewModel.onWishDescriptionChange(it) }

            Spacer(Modifier.height(10.dp))

            Button(onClick = {
                if (viewModel.wishTitleState.isEmpty() || viewModel.wishDescriptionState.isEmpty()) {
                    snackMessage.value = "Please fill all fields"
                } else if (id != 0L) {
                    viewModel.updateWish(
                        Wish(
                            id = id,
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        )
                    )
                    snackMessage.value = "Wish has been updated"
                } else {
                    viewModel.addWish(
                        Wish(
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        )
                    )
                    snackMessage.value = "Wish has been added"
                }

                scope.launch {
                    snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            }) {
                Text(text = if (id != 0L) "Save" else "Add", style = TextStyle(fontSize = 18.sp))
            }
        }
    }
}


@Composable
fun WishTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun WishTextFieldPreview() {
    WishTextField(label = "Title", value = "") {}
}