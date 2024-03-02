package com.system.traffic.domain.repository

import com.system.traffic.domain.model.LineModel
import kotlinx.coroutines.flow.Flow

interface LikeLineRepository {

    // 즐겨찾기 추가
    suspend fun addLikeLine(lineModel: LineModel)

    // 즐겨찾기 삭제
    suspend fun deleteLikeLine(lineId: String)

    // 즐겨찾기 조회
    fun getLikeLineList(): Flow<List<LineModel>>
}