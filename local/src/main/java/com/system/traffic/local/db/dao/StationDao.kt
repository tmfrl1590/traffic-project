package com.system.traffic.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.local.db.model.StationLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(stationEntity: StationLocal)

    // 검색어에 해당하는 정류장 리스트와 즐겨찾기 테이블에 저장되있는 정류장 리스트를 합쳐서 가져옴
    @Query("SELECT * FROM  station_entity WHERE busStopName LIKE :text")
    fun getSearchedStationList(text : String) : List<StationLocal>

    // 정류장1개 정보 가져오기 - nullable로 변경
    @Query("SELECT * FROM station_entity WHERE arsId = :arsId LIMIT 1")
    fun getStationInfo(arsId: String): Flow<StationLocal?>

}