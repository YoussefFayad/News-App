package com.example.newsapp.api

import com.example.newsapp.model.api.ArticlesResponse
import com.example.newsapp.model.api.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewService {

    @GET("/v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey") apiKey:String,
        @Query("category") category:String,
    ): Call<SourcesResponse>

    @GET("/v2/everything")
    fun getArticles(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources :String
    ) : Call<ArticlesResponse>

    @GET("everything")
    fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("searchIn") topic: String,
        @Query("q") searchQuery: String? = null,
    ): Call<ArticlesResponse>


}