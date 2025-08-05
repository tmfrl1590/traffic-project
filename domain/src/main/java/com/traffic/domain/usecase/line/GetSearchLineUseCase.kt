package com.traffic.domain.usecase.line

import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchLineUseCase @Inject constructor(
    private val lineRepository: LineRepository,
) {
    operator fun invoke(keyword: String): Flow<List<LineModel>>{
        return lineRepository.getSearchedLineList(keyword)
    }
}