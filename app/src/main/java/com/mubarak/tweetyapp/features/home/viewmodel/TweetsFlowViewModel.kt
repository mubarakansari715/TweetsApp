package com.mubarak.tweetyapp.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.tweetyapp.features.home.NetworkResponse
import com.mubarak.tweetyapp.features.home.model.TweetItem
import com.mubarak.tweetyapp.features.home.repository.TweetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TweetsFlowViewModel @Inject constructor(
    private val tweetsRepository: TweetsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        getCategoriesNew()
        val category = savedStateHandle.get<String>("categoryName") ?: ""
        getTweetsNew(category)
    }

    private val _categories = MutableStateFlow<List<String>?>(emptyList())
    val categories: StateFlow<List<String>?> = _categories

    private val _tweets = MutableStateFlow<List<TweetItem>>(emptyList())
    val tweets: StateFlow<List<TweetItem>> = _tweets

    fun getTweetsTitle() = savedStateHandle.get<String>("categoryName") ?: "No Sub data"

    fun getCategoriesNew() {
        tweetsRepository.getCategoriesNewFlow().onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Log.e("TAG", "getTTNew: Loading")
                }

                is NetworkResponse.Error -> {
                    Log.e("TAG", "getTTNew: Error ${result.errorMessage}")
                }

                is NetworkResponse.Success -> {
                    Log.e("TAG", "getTTNew: Success ${result.result?.distinct()}")
                    result.result?.let {
                        _categories.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getTweetsNew(categoryName: String) {
        tweetsRepository.getTweetsNewFlow(categoryName).onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Log.e("TAG", "getTTNew: Loading")
                }

                is NetworkResponse.Error -> {
                    Log.e("TAG", "getTTNew: Error ${result.errorMessage}")
                }

                is NetworkResponse.Success -> {
                    Log.e("TAG", "getTTNew: Success ${result.result?.distinct()}")
                    result.result?.let {
                        _tweets.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}