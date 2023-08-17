package com.sunkitto.news.core.domain.repository

import com.sunkitto.news.core.model.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {

    fun getRecentSearches(): Flow<List<RecentSearch>>

    suspend fun upsertRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteRecentSearch(recentSearch: RecentSearch)

    suspend fun deleteAllRecentSearches()
}