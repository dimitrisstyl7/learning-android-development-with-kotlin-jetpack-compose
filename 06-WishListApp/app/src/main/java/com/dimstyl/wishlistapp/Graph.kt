package com.dimstyl.wishlistapp

import android.content.Context
import androidx.room.Room
import com.dimstyl.wishlistapp.data.WishListDatabase
import com.dimstyl.wishlistapp.data.WishRepository

object Graph {

    private lateinit var db: WishListDatabase

    val wishRepository by lazy { WishRepository(db.wishDao()) }

    fun provide(context: Context) {
        db = Room.databaseBuilder(context, WishListDatabase::class.java, "wishlist.db").build()
    }

}