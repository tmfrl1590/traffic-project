package com.traffic.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.traffic.common.Constants.TRAFFIC_DATABASE_NAME
import com.traffic.data.local.db.dao.LikeStationDao
import com.traffic.data.local.db.dao.LineDao
import com.traffic.data.local.db.dao.StationDao
import com.traffic.data.local.db.entity.LikeStationEntity
import com.traffic.data.local.db.entity.LineEntity
import com.traffic.data.local.db.entity.StationEntity

@Database(
    entities = [StationEntity::class, LineEntity::class, LikeStationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrafficDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = TRAFFIC_DATABASE_NAME
    }

    abstract fun stationDAO() : StationDao

    abstract fun lineDAO() : LineDao

    abstract fun likeStationDAO() : LikeStationDao
}