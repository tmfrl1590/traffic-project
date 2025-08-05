package com.traffic.data.local

import com.traffic.data.model.local.LineEntity
import com.traffic.data.model.local.StationEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun setUpIsFirstLogin()

    suspend fun getIsFirstLogin(): Boolean

    // 즐겨찾기 추가
    suspend fun addLikeStation(stationEntity: StationEntity)

    // 즐겨찾기 삭제
    suspend fun deleteLikeStation(arsId: String)

    // 즐겨찾기 가져오기(정류장)
    fun getLikeStationList(): Flow<List<StationEntity>>

    // 정류장 검색 리스트
    fun getSearchedStationList(keyword: String): List<StationEntity>

    // 정류장 정보
    fun getStationInfo(arsId: String): Flow<StationEntity>

    // JSON 파일에서 정류장 데이터 읽기
    fun getStationFileData(): List<StationEntity>

    // 정류장 데이터 DB에 저장
    fun insertStation(stationEntity: StationEntity)

    // JSON 파일에서 노선 데이터 읽기
    fun getLineFileData(): List<LineEntity>

    // 노선 데이터 DB에 저장
    fun insertLine(lineEntity: LineEntity)
}