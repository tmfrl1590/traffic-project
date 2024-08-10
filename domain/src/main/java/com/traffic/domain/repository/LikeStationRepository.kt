package com.traffic.domain.repository

import com.traffic.domain.model.StationModel
import kotlinx.coroutines.flow.Flow

interface LikeStationRepository {

    // 즐겨찾기 추가
    suspend fun addLikeStation(stationModel: StationModel)

    // 즐겨찾기 삭제
    suspend fun deleteLikeStation(arsId: String)

    // 즐겨찾기 가져오기(정류장)
    fun getLikeStationList(): Flow<List<StationModel>>
}