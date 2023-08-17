package com.sunkitto.news.core.network.retrofit

import com.sunkitto.news.core.network.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Fetches News from public News API
 * @see <a href="https://newsapi.org/">News API</a>
 */
interface NewsService {

    /**
     * Returns: top headlines by published date descending.
     */
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("category")
        category: String,
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int,
    ): NewsDto

    /**
     * Returns: all news by published date descending.
     */
    @GET("/v2/everything")
    suspend fun getAllNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int,
        @Query("pageSize")
        pageSize: Int,
    ): NewsDto

    companion object {
        const val DEFAULT_TOP_HEADLINES_PAGE_SIZE = 20
        const val MAX_TOP_HEADLINES_PAGE_SIZE = 100

        const val DEFAULT_ALL_NEWS_PAGE_SIZE = 100
        const val MAX_ALL_NEWS_PAGE_SIZE = 100
    }
}