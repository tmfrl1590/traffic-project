package com.system.traffic.domain.useCase

import com.system.traffic.data.repository.Repository
import com.system.traffic.domain.dataModel.LineModel
import com.system.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LineUseCase (
    //private val repository: Repository
    private val lineRepository: LineRepository
){

    /*fun getLineColor(): Flow<List<LineModel>>{
        return lineRepository.getLineColor()
    }*/

}