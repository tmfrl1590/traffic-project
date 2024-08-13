package com.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.traffic.data.local.db.entity.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(stationEntity: StationEntity)

    @Query("SELECT * FROM station_entity WHERE busStopName LIKE :text")
    fun getSearchedStationList(text : String) : Flow<List<StationEntity>>  // 넘어오는 String 값에 %% 붙여서 보내줘야함

    // 정류장1개 정보 가져오기
    @Query("SELECT * FROM station_entity WHERE busStopId =:arsId")
    fun getStationInfo(arsId : String) : Flow<StationEntity>

}

