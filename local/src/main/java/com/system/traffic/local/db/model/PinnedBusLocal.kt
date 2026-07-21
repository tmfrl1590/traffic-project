package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.system.traffic.local.LocalMapper
import com.system.traffic.local.RoomConstant
import com.traffic.data.model.local.PinnedBusEntity

@Entity(
    tableName = RoomConstant.Table.PINNED_BUS_LOCAL,
    indices = [Index(value = ["busStopId", "lineId"], unique = true)]
)
data class PinnedBusLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val busStopId: String,
    val lineId: String,
    val createdAt: Long = System.currentTimeMillis()
): LocalMapper<PinnedBusEntity>{
    override fun toData(): PinnedBusEntity = PinnedBusEntity(
        id = id,
        busStopId = busStopId,
        lineId = lineId,
        createdAt = createdAt,
    )
}

fun PinnedBusLocal.toPinnedBusModel(): PinnedBusEntity {
    return PinnedBusEntity(
        id = id,
        busStopId = busStopId,
        lineId = lineId,
        createdAt = createdAt,
    )
}