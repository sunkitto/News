package com.sunkitto.news.core.data.repository

import com.sunkitto.news.core.data.asRecentSearch
import com.sunkitto.news.core.data.asRecentSearchEntity
import com.sunkitto.news.core.database.dao.RecentSearchDao
import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import com.sunkitto.news.core.model.RecentSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
) : RecentSearchRepository {

    override fun getRecentSearches(): Flow<List<RecentSearch>> =
        recentSearchDao.getRecentSearches().map { recentSearches ->
            recentSearches.map { recentSearchEntity ->
                recentSearchEntity.asRecentSearch()
            }
        }

    override suspend fun upsertRecentSearch(recentSearch: RecentSearch) =
        recentSearchDao.upsertRecentSearch(recentSearch = recentSearch.asRecentSearchEntity())

    override suspend fun deleteRecentSearch(recentSearch: RecentSearch) =
        recentSearchDao.deleteRecentSearch(recentSearch = recentSearch.asRecentSearchEntity())

    override suspend fun deleteAllRecentSearches() =
        recentSearchDao.deleteAllRecentSearches()
}