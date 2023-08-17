package com.sunkitto.news.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sunkitto.news.core.database.model.all_news.AllNewsRemoteKey

@Dao
interface AllNewsRemoteKeyDao {

    @Upsert
    suspend fun upsertRemoteKeys(remoteKeys: List<AllNewsRemoteKey>)

    @Query("SELECT * FROM all_news_remote_keys WHERE articleUrl = :articleUrl")
    suspend fun getRemoteKey(articleUrl: String): AllNewsRemoteKey

    @Query("DELETE FROM all_news_remote_keys")
    suspend fun deleteAllRemoteKeys()
}