package com.traffic.data.model.local

import com.traffic.data.DataMapper
import com.traffic.domain.model.StationCoordinateModel

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
