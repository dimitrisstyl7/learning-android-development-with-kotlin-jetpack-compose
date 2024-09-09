package com.dimstyl.musicappui.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dimstyl.musicappui.Navigation
import com.dimstyl.musicappui.R
import com.dimstyl.musicappui.Screen
import com.dimstyl.musicappui.bottomNavScreens
import com.dimstyl.musicappui.drawerNavScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val controller: NavController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val currentScreen by viewModel.currentScreen
    val title = remember { mutableStateOf(currentScreen.title) }
    val dialogOpen = remember { mutableStateOf(false) }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.surface) {
                Row(
                    Modifier
                        .background(colorResource(R.color.purple_200))
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "MusicAppUI",
                        modifier = Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    items(drawerNavScreens) {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            },
                            badge = {
                                Icon(
                                    painter = painterResource((it.icon)),
                                    contentDescription = it.title,
                                    modifier = Modifier.padding(top = 4.dp, end = 8.dp)
                                )
                            },
                            selected = currentScreen !is Screen.DrawerNavScreen.AddAccount
                                    && currentScreen.route == it.route,
                            onClick = {
                                if (it.route == Screen.DrawerNavScreen.AddAccount.route) {
                                    dialogOpen.value = true
                                } else {
                                    title.value = it.title
                                    viewModel.setCurrentScreen(it)
                                    controller.navigate(it.route)
                                }
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title.value) },
                    actions = {
                        IconButton(onClick = {
                            scope.launch { modalBottomSheetState.show() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Expand menu"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.purple_200)
                    )
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomNavScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(screen.icon),
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title) },
                            selected = currentScreen == screen,
                            onClick = {
                                title.value = screen.title
                                viewModel.setCurrentScreen(screen)
                                controller.navigate(screen.route)
                            }
                        )
                    }
                }
            }
        ) {
            Navigation(controller, it)
            AccountDialog(dialogOpen)
            CustomModalBottomSheet(modalBottomSheetState, scope)
        }
    }
}

@Composable
fun AccountDialog(dialogOpen: MutableState<Boolean>) {
    if (!dialogOpen.value) return

    AlertDialog(
        title = { Text("Add Account") },
        text = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier.padding(16.dp),
                    value = "",
                    onValueChange = {},
                    label = { Text("Email") })
                TextField(
                    modifier = Modifier.padding(16.dp),
                    value = "",
                    onValueChange = {},
                    label = { Text("Password") })
            }
        },
        onDismissRequest = { dialogOpen.value = false },
        confirmButton = {
            TextButton(onClick = { dialogOpen.value = false }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { dialogOpen.value = false }) {
                Text("Dismiss")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottomSheet(sheetState: SheetState, scope: CoroutineScope) {
    if (!sheetState.isVisible) return

    val icons = listOf(
        Icons.Rounded.Settings to "Settings",
        Icons.Rounded.Share to "Share",
        ImageVector.vectorResource(R.drawable.round_help_24) to "Help"
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState
    ) {
        Column(Modifier.navigationBarsPadding()) {
            icons.forEach {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .clickable {}
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 18.dp)
                ) {
                    Icon(imageVector = it.first, contentDescription = "${it.second} icon")
                    Text(text = it.second)
                }
            }
        }
    }
}