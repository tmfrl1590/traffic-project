package com.traffic.domain.usecase.line

import com.traffic.domain.repository.LineRepository
import javax.inject.Inject

class GetLineKindUseCase @Inject constructor(
    private val lineRepository: LineRepository,
) {
    operator fun invoke(lineId: String): String{
        return lineRepository.getLineKind(lineId)
    }
}