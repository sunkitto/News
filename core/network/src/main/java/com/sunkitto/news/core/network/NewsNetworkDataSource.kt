package com.sunkitto.news.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sunkitto.news.core.network.model.NewsDto
import com.sunkitto.news.core.network.retrofit.NewsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = "https://newsapi.org"
private const val MEDIA_TYPE = "application/json"
private const val API_KEY_HEADER = "X-Api-Key"

interface NewsNetworkDataSource {

    suspend fun getTopHeadlines(
        country: String,
        category: String,
        page: Int,
        pageSize: Int,
    ): NewsDto

    suspend fun getAllNews(
        query: String,
        page: Int,
        pageSize: Int,
    ): NewsDto
}

class NewsNetworkDataSourceImpl @Inject constructor(
    private val apiKey: String
) : NewsNetworkDataSource {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val okHttpCallFactory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            },
        )
        .addInterceptor { chain ->
            val authorizedRequest = chain.request().newBuilder()
                .addHeader(API_KEY_HEADER, apiKey)
                .build()
            chain.proceed(authorizedRequest)
        }
        .build()

    private val newsService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            json.asConverterFactory(MEDIA_TYPE.toMediaType()),
        )
        .build()
        .create(NewsService::class.java)

    override suspend fun getTopHeadlines(
        country: String,
        category: String,
        page: Int,
        pageSize: Int,
    ) =
        newsService.getTopHeadlines(country, category, page, pageSize)

    override suspend fun getAllNews(
        query: String,
        page: Int,
        pageSize: Int,
    ) =
        newsService.getAllNews(query, page, pageSize)
}