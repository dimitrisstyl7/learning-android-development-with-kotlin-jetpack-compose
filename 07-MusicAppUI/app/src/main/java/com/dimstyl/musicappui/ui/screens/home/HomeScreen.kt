package com.dimstyl.musicappui.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimstyl.musicappui.dummyMusicGenres
import com.dimstyl.musicappui.dummySections
import com.dimstyl.musicappui.ui.components.MusicGenreItemCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    LazyColumn {
        dummySections.forEach {
            stickyHeader {
                Text(text = it, modifier = Modifier.padding(16.dp))
                LazyRow {
                    items(dummyMusicGenres.shuffled()) {
                        MusicGenreItemCard(it)
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}