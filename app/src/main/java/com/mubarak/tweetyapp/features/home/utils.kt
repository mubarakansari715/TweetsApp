package com.mubarak.tweetyapp.features.home

import com.mubarak.tweetyapp.features.home.model.TweetItem

sealed class NetworkResponse<out T : Any?> {
    object Loading : NetworkResponse<Nothing>()
    data class Error(val errorMessage: String, val errorDetails: Any?= null) : NetworkResponse<Nothing>()
    data class Success<out T : Any>(val result: T?) : NetworkResponse<T>()
}

sealed class CategoryState {
    object Loading : CategoryState()
    object Error : CategoryState()
    class Success(val response: List<String>) : CategoryState()
}

sealed class CategoryDetails {
    object Loading : CategoryDetails()
    object Error : CategoryDetails()
    class Success(val response: List<TweetItem>) : CategoryDetails()
}