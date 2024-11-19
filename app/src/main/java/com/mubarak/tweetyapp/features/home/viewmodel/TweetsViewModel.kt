package com.mubarak.tweetyapp.features.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.tweetyapp.features.home.model.TweetItem
import com.mubarak.tweetyapp.features.home.repository.TweetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val tweetsRepository: TweetsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            getCategories()

            val category = savedStateHandle.get<String>("categoryName") ?: ""
            getTweets(category)
        }
    }

    private val _categories = MutableStateFlow<List<String>?>(emptyList())
    val categories: StateFlow<List<String>?> = _categories

    private val _tweets = MutableStateFlow<List<TweetItem>>(emptyList())
    val tweets: StateFlow<List<TweetItem>> = _tweets

    suspend fun getCategories() {
        if (tweetsRepository.getCategories().isEmpty()) {
            _categories.value = emptyList()
            return
        }
        _categories.value = tweetsRepository.getCategories()
    }

    suspend fun getTweets(category: String) {
        if (tweetsRepository.getTweets(category).isEmpty()) {
            _tweets.value = emptyList()
            return
        }
        _tweets.value = tweetsRepository.getTweets(category)
    }

    fun getTweetsTitle() = savedStateHandle.get<String>("categoryName") ?: "No Sub data"
}