package com.sunkitto.news.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sunkitto.news.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recent_searches ORDER BY date DESC")
    fun getRecentSearches(): Flow<List<RecentSearchEntity>>

    @Upsert
    suspend fun upsertRecentSearch(recentSearch: RecentSearchEntity)

    @Delete
    suspend fun deleteRecentSearch(recentSearch: RecentSearchEntity)

    @Query("DELETE FROM recent_searches")
    suspend fun deleteAllRecentSearches()
}