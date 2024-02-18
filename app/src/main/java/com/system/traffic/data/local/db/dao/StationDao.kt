package com.system.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.system.traffic.domain.dataModel.BusArriveBody
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.network.TrafficApi
import com.system.traffic.network.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(stationEntity: StationEntity)

    // 정류장 - 즐겨찾기 목록 가져오기
    @Query("SELECT * FROM station WHERE selected = '1'")
    fun getLikeStationList() : Flow<List<StationEntity>>

    @Query("SELECT * FROM station WHERE busstop_name LIKE :text" )
    fun getSearchedStationList(text : String) : List<StationEntity>  // 넘어오는 String 값에 %% 붙여서 보내줘야함

    // 정류장 - 즐겨찾기 추가
    @Update
    fun updateStation(stationEntity: StationEntity)

    // 정류장1개 정보 가져오기
    @Query("SELECT * FROM station WHERE busstop_id =:ars_id")
    fun getStationInfo(ars_id : String?) : Flow<StationEntity>

}

