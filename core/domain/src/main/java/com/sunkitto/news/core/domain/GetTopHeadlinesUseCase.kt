package com.sunkitto.news.core.domain

import androidx.paging.PagingData
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.TopHeadlinesCategory
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {

    operator fun invoke(category: TopHeadlinesCategory): Flow<PagingData<Article>> =
        newsRepository.getTopHeadlines(category = category)
}