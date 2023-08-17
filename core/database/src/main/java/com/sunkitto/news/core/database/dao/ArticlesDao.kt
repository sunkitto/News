package com.sunkitto.news.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sunkitto.news.core.database.model.all_news.ArticleEntity

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles ORDER BY published_at DESC")
    fun getArticles(): PagingSource<Int, ArticleEntity>

    @Insert
    suspend fun insertArticles(articleEntities: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    @Transaction
    suspend fun deleteAllAndInsertNewArticles(
        articleEntities: List<ArticleEntity>
    ) {
        deleteAllArticles()
        insertArticles(articleEntities)
    }
}