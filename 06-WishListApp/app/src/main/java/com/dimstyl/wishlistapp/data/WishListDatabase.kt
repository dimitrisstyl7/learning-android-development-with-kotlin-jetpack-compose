package com.dimstyl.wishlistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Wish::class], version = 1, exportSchema = false)
abstract class WishListDatabase : RoomDatabase() {

    abstract fun wishDao(): WishDao

}