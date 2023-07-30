package com.sunkitto.news.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sunkitto.news.core.database.model.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY published_at DESC")
    fun getNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE title LIKE :query OR description LIKE :query")
    fun searchNews(query: String): Flow<List<NewsEntity>>

    @Upsert
    suspend fun upsertNews(newsEntities: List<NewsEntity>)
}
