package com.sunkitto.news.core.domain

import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import javax.inject.Inject

class GetRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository,
) {

    operator fun invoke() = recentSearchRepository.getRecentSearches()
}