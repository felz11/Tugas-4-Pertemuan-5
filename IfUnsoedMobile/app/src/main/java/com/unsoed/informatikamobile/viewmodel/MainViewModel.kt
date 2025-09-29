package com.unsoed.informatikamobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsoed.informatikamobile.data.model.BookDoc
import com.unsoed.informatikamobile.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import android.util.Log

class MainViewModel : ViewModel() {
    private val _books = MutableLiveData<List<BookDoc>>()
    val books: LiveData<List<BookDoc>> get() = _books

    fun fetchBooks(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchBooks(query, 10)
                if (response.isSuccessful) {
                    _books.value = response.body()?.docs ?: emptyList()
                } else {
                    Log.e("MainViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception: ${e.message}")
            }
        }
    }
}
