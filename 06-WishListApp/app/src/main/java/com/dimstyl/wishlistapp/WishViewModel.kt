package com.dimstyl.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimstyl.wishlistapp.data.Wish
import com.dimstyl.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(private val wishRepository: WishRepository = Graph.wishRepository) :
    ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")
    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWishes = wishRepository.getAllWishes()
        }
    }

    fun onWishTitleChange(title: String) {
        wishTitleState = title
    }

    fun onWishDescriptionChange(description: String) {
        wishDescriptionState = description
    }

    fun getWishById(id: Long) = wishRepository.getWishById(id)

    fun addWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }

}