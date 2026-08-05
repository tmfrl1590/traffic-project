package com.system.traffic.data.model.local

import com.system.traffic.data.DataMapper
import com.system.traffic.domain.model.StationCoordinateModel

data class StationCoordinates(
    val busStopId: String?,
    val latitude: String?,
    val longitude: String?
): DataMapper<StationCoordinateModel>{
    override fun toDomain(): StationCoordinateModel {
        return StationCoordinateModel(
            busStopId = busStopId,
            latitude = latitude,
            longitude = longitude,
        )
    }
}
