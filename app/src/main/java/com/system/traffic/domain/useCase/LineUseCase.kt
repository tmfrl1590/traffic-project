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

    fun updateLine(lineModel: LineModel){
        lineRepository.updateLine(lineModel)
    }

}