package com.dimstyl.wishlistapp.data

class WishRepository(private val wishDao: WishDao) {

    fun getAllWishes() = wishDao.getAll()
    fun getWishById(id: Long) = wishDao.getById(id)
    suspend fun addWish(wish: Wish) = wishDao.insert(wish)
    suspend fun updateWish(wish: Wish) = wishDao.update(wish)
    suspend fun deleteWish(wish: Wish) = wishDao.delete(wish)

}