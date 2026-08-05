package com.system.traffic.domain.usecase.keyword

import com.system.traffic.domain.repository.KeywordRepository
import javax.inject.Inject

class GetKeywordListUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    suspend operator fun invoke() = keywordRepository.getKeywordList()
}