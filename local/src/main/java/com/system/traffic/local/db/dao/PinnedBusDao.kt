package com.system.traffic.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.system.traffic.local.db.model.PinnedBusLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface PinnedBusDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPinnedBus(pinnedBusLocal: PinnedBusLocal)

    // 삭제
    @Query(
        """
            DELETE FROM pinned_bus_entity WHERE busStopId = :busStopId AND lineId = :lineId
        """
    )
    suspend fun deletePinnedBus(busStopId: String, lineId: String)

    // 조회
    @Query(
        """
            SELECT * FROM pinned_bus_entity WHERE busStopId = :busStopId
        """
    )
    fun getPinnedBus(busStopId: String): Flow<List<PinnedBusLocal>>
}