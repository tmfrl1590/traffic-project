package com.system.traffic.domain.useCase

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow

class LineUseCase (
    private val lineRepository: LineRepository
){

    fun getLineColor(): Flow<List<LineModel>> {
        return lineRepository.getLineColor()
    }

    fun getSearchedLineList(keyword: String): Flow<List<LineModel>> {
        return lineRepository.getSearchedLineList(keyword)
    }

    // 노선 한건 조회
    fun getLineOne(lineId: String): LineModel {
        return lineRepository.getLineOne(lineId)
    }
}