package com.sunkitto.news.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sunkitto.news.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recent_searches ORDER BY date DESC LIMIT :limit")
    fun getRecentSearches(limit: Int): Flow<List<RecentSearchEntity>>

    @Upsert
    suspend fun upsertRecentSearches(recentSearch: RecentSearchEntity)

    @Query("DELETE FROM recent_searches")
    suspend fun deleteRecentSearches()
}