package com.dimstyl.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wish")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo
    val title: String = "",

    @ColumnInfo
    val description: String = "",
)