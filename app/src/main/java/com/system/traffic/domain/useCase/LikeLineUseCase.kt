package com.system.traffic.domain.useCase

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.LikeLineRepository
import kotlinx.coroutines.flow.Flow

class LikeLineUseCase(
    private val likeLineRepository: LikeLineRepository,
) {

    suspend fun addLikeLine(lineModel: LineModel){
        likeLineRepository.addLikeLine(lineModel)
    }

    suspend fun deleteLikeLine(lineId: String){
        likeLineRepository.deleteLikeLine(lineId)
    }

    fun getLikeLineList(): Flow<List<LineModel>>{
        return likeLineRepository.getLikeLineList()
    }
}