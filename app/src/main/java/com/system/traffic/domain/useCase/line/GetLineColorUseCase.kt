package com.system.traffic.domain.useCase.line

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLineColorUseCase @Inject constructor(
    private val lineRepository: LineRepository,
){
    operator fun invoke(): Flow<List<LineModel>>{
        return lineRepository.getLineColor()
    }
}