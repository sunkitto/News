package com.sunkitto.news.core.database

import androidx.room.Room
import com.sunkitto.news.core.database.dao.RecentSearchDao
import com.sunkitto.news.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class RecentSearchDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var recentSearchDao: RecentSearchDao

    @Before
    fun create_database() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        database = Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java,
        ).allowMainThreadQueries().build()
        recentSearchDao = database.recentSearchDao()
    }

    @Test
    fun write_and_read_recent_searches_by_descending_date() = runTest {
        val fakeRecentSearchEntities = listOf(
            RecentSearchEntity(
                query = "Test query 2",
                date = Instant.fromEpochMilliseconds(3),
            ),
            RecentSearchEntity(
                query = "Test query 3",
                date = Instant.fromEpochMilliseconds(2),
            ),
            RecentSearchEntity(
                query = "Test query 4",
                date = Instant.fromEpochMilliseconds(5),
            ),
            RecentSearchEntity(
                query = "Test query 5",
                date = Instant.fromEpochMilliseconds(4),
            ),
        )
        for (recentSearch in fakeRecentSearchEntities) {
            recentSearchDao.upsertRecentSearch(
                recentSearch,
            )
        }
        val orderedRecentSearches = recentSearchDao.getRecentSearches().first()
        assertEquals(
            listOf(5L, 4L, 3L, 2L),
            orderedRecentSearches.map { recentSearch -> recentSearch.date.toEpochMilliseconds() },
        )
    }

    @Test
    fun delete_single_recent_search() = runTest {
        val fakeRecentSearchEntity = RecentSearchEntity(
            query = "Test query",
            date = Instant.fromEpochMilliseconds(1),
        )
        recentSearchDao.upsertRecentSearch(fakeRecentSearchEntity)
        recentSearchDao.deleteRecentSearch(fakeRecentSearchEntity)
        val recentSearches = recentSearchDao.getRecentSearches()
        assertTrue(recentSearches.first().isEmpty())
    }

    @Test
    fun delete_all_recent_searches() = runTest {
        val fakeRecentSearchEntities = listOf(
            RecentSearchEntity(
                query = "Test query",
                date = Instant.fromEpochMilliseconds(1),
            ),
            RecentSearchEntity(
                query = "Test query 2",
                date = Instant.fromEpochMilliseconds(1),
            ),
        )
        for (recentSearch in fakeRecentSearchEntities) {
            recentSearchDao.upsertRecentSearch(
                recentSearch,
            )
        }
        recentSearchDao.deleteAllRecentSearches()
        val recentSearches = recentSearchDao.getRecentSearches()
        assertTrue(recentSearches.first().isEmpty())
    }

    @After
    fun close_database() {
        database.close()
    }
}