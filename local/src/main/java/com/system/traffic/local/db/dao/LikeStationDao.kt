package com.system.traffic.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.local.db.model.LikeStationLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeStationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikeStation(likeStationLocal: LikeStationLocal)

    @Query("DELETE FROM station_like_local WHERE ars_id = :arsId")
    suspend fun deleteLikeStation(arsId : String)

    // 즐겨찾기 목록 조회
    @Query("SELECT * FROM station_like_local")
    fun getLikeStationList() : Flow<List<LikeStationLocal>>
}