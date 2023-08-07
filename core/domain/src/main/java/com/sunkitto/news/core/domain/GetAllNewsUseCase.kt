package com.sunkitto.news.core.domain

import com.sunkitto.news.core.domain.repository.NewsRepository
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(query: String) = newsRepository.getAllNews(query = query)
}