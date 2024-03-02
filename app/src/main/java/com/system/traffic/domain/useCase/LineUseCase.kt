package com.system.traffic.domain.useCase

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.LineRepository

class LineUseCase (
    //private val repository: Repository
    private val lineRepository: LineRepository
){

    /*fun getLineColor(): Flow<List<LineModel>>{
        return lineRepository.getLineColor()
    }*/

    // 노선 한건 조회
    fun getLineOne(lineId: String): LineModel {
        return lineRepository.getLineOne(lineId)
    }
}