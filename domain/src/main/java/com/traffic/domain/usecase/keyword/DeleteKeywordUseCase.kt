package com.traffic.domain.usecase.keyword

import com.traffic.domain.repository.KeywordRepository
import javax.inject.Inject

class DeleteKeywordUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
){
    suspend operator fun invoke(keyword: String) = keywordRepository.deleteKeyword(keyword = keyword)
}