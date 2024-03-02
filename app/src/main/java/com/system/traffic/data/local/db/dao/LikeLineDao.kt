package com.system.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.data.local.db.entity.LikeLineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeLineDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikeLine(likeLineEntity: LikeLineEntity)

    @Query("DELETE FROM line_like_entity WHERE line_id =:lineId")
    suspend fun deleteLikeLine(lineId: String)

    // 즐겨찾기 목록 조회
    @Query("SELECT * FROM line_like_entity")
    fun getLikeLineList(): Flow<List<LikeLineEntity>>
}