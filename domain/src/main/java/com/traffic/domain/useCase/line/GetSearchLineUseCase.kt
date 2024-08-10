package com.traffic.domain.useCase.line

import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchLineUseCase @Inject constructor(
    private val lineRepository: com.traffic.domain.repository.LineRepository,
) {
    operator fun invoke(keyword: String): Flow<List<com.traffic.domain.model.LineModel>>{
        return lineRepository.getSearchedLineList(keyword)
    }
}