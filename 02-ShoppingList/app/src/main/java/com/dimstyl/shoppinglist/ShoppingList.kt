package com.dimstyl.shoppinglist

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun ShoppingListApp(
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String
) {
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    fun clearInputFields() {
        showDialog = false
        itemName = ""
        itemQuantity = ""
    }

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

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        // Create a button for adding items.
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")
        }

        // Display the list of items.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems) { item ->
                if (item.isEditing) {
                    ShoppingItemEditor(item = item, onEditComplete = { editedName, editedQuantity ->
                        sItems = sItems.map {
                            if (it.id == item.id) {
                                it.copy(
                                    name = editedName,
                                    quantity = editedQuantity,
                                    isEditing = false,
                                    address = address
                                )
                            } else it
                        }
                    })
                } else {
                    ShoppingListItem(
                        item = item,
                        onEditClick = {
                            sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                        },
                        onDeleteClick = {
                            sItems = sItems - item
                        }
                    )
                }
            }
        }

        // Dialog for adding a new item.
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { clearInputFields() },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Create a button for adding a specific item to the shopping list.
                        Button(onClick = {
                            val itemQuantityInt: Int? = itemQuantity.toIntOrNull()
                            if (itemName.isBlank() || itemQuantityInt == null) return@Button
                            if (itemQuantityInt > 0) {
                                val newItem =
                                    ShoppingItem(
                                        sItems.size + 1,
                                        itemName,
                                        itemQuantityInt,
                                        address = address
                                    )
                                sItems = sItems + newItem
                                clearInputFields()
                            }
                        }) {
                            Text("Add")
                        }

                        // Create a cancellation button for item addition.
                        Button(onClick = { clearInputFields() }) {
                            Text("Cancel")
                        }
                    }
                },
                title = { Text("Add Shopping item") },
                text = {
                    Column {
                        // OutlinedTextField for item name.
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )

                        // OutlinedTextField for item quantity.
                        OutlinedTextField(
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )

                        Button(onClick = {
                            if (LocationUtil.hasLocationPermission(context)) {
                                LocationUtil.requestLocationUpdates(viewModel, context)
                                navController.navigate("locationScreen") {
                                    launchSingleTop = true
                                }
                            } else {
                                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            }
                        }) {
                            Text("Address")
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = Color(
                        red = Random.nextInt(256),
                        green = Random.nextInt(256),
                        blue = Random.nextInt(256)
                    )
                ),
                shape = RoundedCornerShape(percent = 20),
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Row {
                Text(text = item.name, modifier = Modifier.padding(8.dp))
                Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location icon")
                Text(text = item.address)
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            // Edit Button
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit item button")
            }

            // Delete Button
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete item button")
            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
private fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit) {
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            // OutlinedTextField for item name editing.
            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // OutlinedTextField for item quantity editing.
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = editedQuantity,
                onValueChange = { editedQuantity = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            // Create a button for saving the edited item.
            Button(onClick = {
                isEditing = false
                val editedQuantityInt: Int? = editedQuantity.toIntOrNull()
                if (editedName.isBlank() || editedQuantityInt == null) return@Button
                onEditComplete(editedName, editedQuantityInt)
            }) {
                Text("Save")
            }
        }
    }
}

private data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var isEditing: Boolean = false,
    var address: String = ""
)