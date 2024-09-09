package com.dimstyl.musicappui.ui.screens.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dimstyl.musicappui.dummyMusicGenres
import com.dimstyl.musicappui.ui.components.MusicGenreItemCard

@Composable
fun BrowseScreen() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(dummyMusicGenres) {
            MusicGenreItemCard(it)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BrowseScreenPreview() {
    BrowseScreen()
}