package com.sunkitto.news.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sunkitto.news.core.database.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles ORDER BY published_at DESC")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE title LIKE :query OR description LIKE :query")
    fun searchArticles(query: String): Flow<List<ArticleEntity>>

    @Upsert
    suspend fun upsertArticles(newsEntities: List<ArticleEntity>)
}
