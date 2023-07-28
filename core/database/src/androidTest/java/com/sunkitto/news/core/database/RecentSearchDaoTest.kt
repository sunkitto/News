package com.sunkitto.news.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sunkitto.news.core.database.dao.RecentSearchDao
import com.sunkitto.news.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RecentSearchDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var recentSearchDao: RecentSearchDao

    @BeforeEach
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java
        ).build()
        recentSearchDao = database.recentSearchDao()
    }

    @Test
    fun writeAndReadRecentSearchesByDescendingDateWithLimit() = runTest {
        val fakeRecentSearchEntities = listOf(
            RecentSearchEntity(
                query = "Test query 2",
                date = Instant.fromEpochMilliseconds(3)
            ),
            RecentSearchEntity(
                query = "Test query 3",
                date = Instant.fromEpochMilliseconds(2)
            ),
            RecentSearchEntity(
                query = "Test query 4",
                date = Instant.fromEpochMilliseconds(5)
            ),
            RecentSearchEntity(
                query = "Test query 5",
                date = Instant.fromEpochMilliseconds(4)
            ),
        )
        for(recentSearch in fakeRecentSearchEntities) {
            recentSearchDao.upsertRecentSearches(
                recentSearch
            )
        }
        val orderedRecentSearches = recentSearchDao.getRecentSearches(limit = 3).first()
        assertEquals(
            listOf(5L, 4L, 3L),
            orderedRecentSearches.map { recentSearch -> recentSearch.date.toEpochMilliseconds() }
        )
    }

    @AfterEach
    fun closeDatabase() {
        database.close()
    }
}