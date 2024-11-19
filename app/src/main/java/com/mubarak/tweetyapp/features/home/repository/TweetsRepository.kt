package com.mubarak.tweetyapp.features.home.repository

import com.mubarak.tweetyapp.features.home.NetworkResponse
import com.mubarak.tweetyapp.features.home.model.TweetItem
import com.mubarak.tweetyapp.features.home.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TweetsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCategories(): List<String> = try {
        val response = apiService.getCategories()
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    suspend fun getTweets(category: String): List<TweetItem> = try {
        val response = apiService.getTweets("tweets[?(@.category==\"$category\")]")
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    fun getCategoriesNewFlow(): Flow<NetworkResponse<List<String>>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
                emit(NetworkResponse.Success(data))
            } else {
                emit(NetworkResponse.Error("Error"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResponse.Error("Error"))
        }
    }

    fun getTweetsNewFlow(categoryName: String): Flow<NetworkResponse<List<TweetItem>>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = apiService.getTweets("tweets[?(@.category==\"$categoryName\")]")
            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
                emit(NetworkResponse.Success(data))
            } else {
                emit(NetworkResponse.Error("Error"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResponse.Error("Error"))
        }
    }
}