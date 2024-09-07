package com.dimstyl.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {

    @Query("SELECT * FROM Wish")
    fun getAll(): Flow<List<Wish>>

    @Query("SELECT * FROM Wish WHERE id = :id")
    fun getById(id: Long): Flow<Wish>

    @Insert
    suspend fun insert(wish: Wish)

    @Update
    suspend fun update(wish: Wish)

    @Delete
    suspend fun delete(wish: Wish)

}