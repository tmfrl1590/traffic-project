package com.system.traffic.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.system.traffic.dataModel.StationModel
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStation(stationEntity: StationEntity)

    @Query("SELECT * FROM station")
    fun getAllStation() : Flow<List<StationEntity>>


    // 정류장 찾기 - 검색
    @Query("SELECT * FROM station WHERE busstop_name LIKE :text ")
    fun getSavedStationList(text : String) : List<StationEntity>

    // 정류장 - 즐겨찾기 목록 가져오기
    @Query("SELECT * FROM station WHERE selected ='1'")
    fun getLikeStationList() : Flow<List<StationEntity>>

    // 즐겨찾기 - 정류장 추가, 삭제
    @Update
    fun updateStation(stationEntity: StationEntity)

    // 정류장1개 정보 가져오기
    @Query("SELECT * FROM station WHERE ars_id =:ars_id")
    fun getStationInfo(ars_id : String?) : StationEntity

    // 즐겨찾기 - 정류장 추가 삭제(상세화면에서)
    @Query("UPDATE station SET selected =:text WHERE ars_id =:ars_id")
    fun updateStationLike(text: String, ars_id: String)

    // 즐겨찾기 - 해당 정류소가 이미 즐겨찾기 되있는지 확인여부
    @Query("SELECT * FROM station WHERE ars_id =:ars_id and selected = '1'")
    fun getIsLikeStation(ars_id: String) : StationEntity

}