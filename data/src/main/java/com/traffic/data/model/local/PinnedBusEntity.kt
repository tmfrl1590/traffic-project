package com.traffic.data.model.local

import com.traffic.domain.model.PinnedBusModel

data class PinnedBusEntity(
    val id: Long = 0,
    val busStopId: String,
    val lineId: String,
    val createdAt: Long = System.currentTimeMillis()
)

fun PinnedBusEntity.toDomain(): PinnedBusModel {
    return PinnedBusModel(
        busStopId = busStopId,
        lineId = lineId
    )
}