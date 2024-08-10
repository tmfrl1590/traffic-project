package com.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.traffic.data.local.db.entity.LikeStationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeStationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikeStation(likeStationEntity: LikeStationEntity)

    @Query("DELETE FROM station_like_entity WHERE busstop_id =:arsId")
    suspend fun deleteLikeStation(arsId : String)

    // 즐겨찾기 목록 조회
    @Query("SELECT * FROM station_like_entity")
    fun getLikeStationList() : Flow<List<LikeStationEntity>>
}