package com.dimstyl.musicappui

import androidx.annotation.DrawableRes

val dummyMusicGenres =
    listOf("POP", "ROCK", "JAZZ", "CLASSICAL", "HIP HOP", "RAP", "R&B", "METAL", "BLUES")

val dummySections = listOf(
    "Recently Played",
    "Top Charts",
    "New Releases",
    "Recommended for You"
)

val musicLibraryItems = listOf(
    MusicLibraryItem(R.drawable.round_music_note_24, "Songs"),
    MusicLibraryItem(R.drawable.baseline_album_24, "Albums"),
    MusicLibraryItem(R.drawable.sharp_artist_24, "Artists"),
    MusicLibraryItem(R.drawable.round_queue_music_24, "Playlists")
)

data class MusicLibraryItem(@DrawableRes val icon: Int, val name: String)