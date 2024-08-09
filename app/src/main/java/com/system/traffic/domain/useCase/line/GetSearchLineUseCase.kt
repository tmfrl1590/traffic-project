package com.system.traffic.domain.useCase.line

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchLineUseCase @Inject constructor(
    private val lineRepository: LineRepository,
) {
    operator fun invoke(keyword: String): Flow<List<LineModel>>{
        return lineRepository.getSearchedLineList(keyword)
    }
}