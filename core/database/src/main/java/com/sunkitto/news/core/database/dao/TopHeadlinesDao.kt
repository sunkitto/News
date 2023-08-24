package com.sunkitto.news.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sunkitto.news.core.database.model.topHeadlines.TopHeadlinesEntity

@Dao
interface TopHeadlinesDao {

    @Query("SELECT * FROM top_headlines ORDER BY published_at DESC")
    fun getTopHeadlines(): PagingSource<Int, TopHeadlinesEntity>

    @Insert
    suspend fun insertTopHeadlines(topHeadlinesEntities: List<TopHeadlinesEntity>)

    @Query("DELETE FROM top_headlines")
    suspend fun deleteAllTopHeadlines()

    @Transaction
    suspend fun deleteAllAndInsertNewTopHeadlines(
        topHeadlinesEntities: List<TopHeadlinesEntity>,
    ) {
        deleteAllTopHeadlines()
        insertTopHeadlines(topHeadlinesEntities)
    }
}