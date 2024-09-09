package com.dimstyl.musicappui.ui.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimstyl.musicappui.MusicLibraryItem
import com.dimstyl.musicappui.dummyMusicLibraryItems

@Composable
fun LibraryScreen() {
    LazyColumn {
        items(dummyMusicLibraryItems) {
            MusicLibraryItemCard(it)
        }
    }
}

@Composable
fun MusicLibraryItemCard(musicLibraryItem: MusicLibraryItem) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(musicLibraryItem.icon),
                    contentDescription = "${musicLibraryItem.name} icon"
                )
                Text(text = musicLibraryItem.name, modifier = Modifier.padding(start = 8.dp))
            }
            Row(horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Navigate to ${musicLibraryItem.name}"
                    )
                }
            }
        }
        HorizontalDivider()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LibraryScreenPreview() {
    LibraryScreen()
}