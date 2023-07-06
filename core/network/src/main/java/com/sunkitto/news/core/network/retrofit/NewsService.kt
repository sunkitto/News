package com.sunkitto.news.core.network.retrofit

import com.sunkitto.news.core.network.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/top-headlines")
    suspend fun getBreakingHeadlines(
        @Query("country")
        country: String,
        @Query("category")
        category: String,
        @Query("page")
        page: Int
    ): NewsDto

    @GET("/v2/everything")
    suspend fun getAllNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int
    ): NewsDto
}