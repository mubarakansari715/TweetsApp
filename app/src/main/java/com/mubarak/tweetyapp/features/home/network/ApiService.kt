package com.mubarak.tweetyapp.features.home.network

import com.mubarak.tweetyapp.features.home.model.TweetItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    @GET("/v3/b/673ade01ad19ca34f8cbd9d1?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String): Response<List<TweetItem>>

    @GET("/v3/b/673ade01ad19ca34f8cbd9d1?meta=false")
    @Headers("X-JSON-Path: tweets..category")
    suspend fun getCategories(): Response<List<String>>
}