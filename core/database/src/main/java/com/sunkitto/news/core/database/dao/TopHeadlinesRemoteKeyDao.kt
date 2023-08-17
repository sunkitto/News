package com.sunkitto.news.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sunkitto.news.core.database.model.top_headlines.TopHeadlinesRemoteKey

@Dao
interface TopHeadlinesRemoteKeyDao {

    @Upsert
    suspend fun upsertRemoteKeys(remoteKeys: List<TopHeadlinesRemoteKey>)

    @Query("SELECT * FROM top_headlines_remote_keys WHERE articleUrl = :articleUrl")
    suspend fun getRemoteKey(articleUrl: String): TopHeadlinesRemoteKey

    @Query("DELETE FROM top_headlines_remote_keys")
    suspend fun deleteAllRemoteKeys()
}