package com.sunkitto.news.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sunkitto.news.core.network.model.NewsDto
import com.sunkitto.news.core.network.retrofit.NewsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://newsapi.org"
private const val MEDIA_TYPE = "application/json"

interface NewsNetworkDataSource {

    suspend fun getBreakingHeadlines(
        country: String,
        category: String,
        page: Int,
    ): NewsDto

    suspend fun getAllNews(
        query: String,
        page: Int,
    ): NewsDto
}

class NewsNetworkDataSourceImpl : NewsNetworkDataSource {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val okHttpCallFactory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if(BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            }
        )
        .build()

    private val newsService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            json.asConverterFactory(MEDIA_TYPE.toMediaType())
        )
        .build()
        .create(NewsService::class.java)

    override suspend fun getBreakingHeadlines(
        country: String,
        category: String,
        page: Int
    ) =
        newsService.getBreakingHeadlines(country, category, page)

    override suspend fun getAllNews(
        query: String,
        page: Int
    ) =
        newsService.getAllNews(query, page)
}