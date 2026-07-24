package com.traffic.data.local

import com.traffic.data.model.local.KeywordEntity
import com.traffic.data.model.local.LineEntity
import com.traffic.data.model.local.PinnedBusEntity
import com.traffic.data.model.local.StationCoordinates
import com.traffic.data.model.local.StationEntity
import com.traffic.domain.model.StationCoordinateModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun setUpIsFirstLogin()

    suspend fun getIsFirstLogin(): Boolean

    suspend fun setAppFontSize(fontSize: String)

    fun getAppFontSize(): Flow<String>

    suspend fun setAppThemeType(themeType: String)

    fun getAppThemeType(): Flow<String>

    // 즐겨찾기 추가
    suspend fun addLikeStation(stationEntity: StationEntity)

    // 즐겨찾기 삭제
    suspend fun deleteLikeStation(arsId: String)

    // 즐겨찾기 가져오기(정류장)
    fun getLikeStationList(): Flow<List<StationEntity>>

    // 정류장 검색 리스트
    suspend fun getSearchedStationList(keyword: String): List<StationEntity>

    // 정류장 정보
    suspend fun getStationInfo(arsId: String): StationEntity

    // JSON 파일에서 정류장 데이터 읽기
    fun getStationFileData(): List<StationEntity>

    // 정류장 데이터 DB에 저장
    fun insertStation(stationEntity: StationEntity)

    // 정류장 데이터 리스트 DB에 벌크 저장
    fun insertStations(stations: List<StationEntity>)

    // JSON 파일에서 노선 데이터 읽기
    fun getLineFileData(): List<LineEntity>

    // 노선 데이터 DB에 저장
    fun insertLine(lineEntity: LineEntity)

    // 노선 데이터 리스트 DB에 벌크 저장
    fun insertLines(lines: List<LineEntity>)

    // 키워드 저장
    suspend fun insertKeyword(keyword: String)

    // 키워드 가져오기
    fun getKeywordList(): Flow<List<KeywordEntity>>

    // 키워드 삭제하기
    suspend fun deleteKeyword(keyword: String)

    // 키워드 전체 삭제
    suspend fun allDeleteKeyword()

    // 특정 정류장의 위치 정보 가져오기
    suspend fun getLocationInfo(ids: List<String?>): List<StationCoordinates>

    // 특정 정류장에 핀 버스 정보 추가
    suspend fun addPinnedBus(busStopId: String, lineId: String)

    // 특정 정류장에 핀 버스 정보 삭제
    suspend fun deletePinnedBus(busStopId: String, lineId: String)

    // 특정 정류장에 핀 버스 정보 가져오기
    fun getPinnedBusList(busStopId: String): Flow<List<PinnedBusEntity>>

    // 저장된 핀 버스 리스트 삭제
    suspend fun resetPinnedBus()
}