package com.system.traffic.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.system.traffic.local.LocalConstants.TRAFFIC_DATABASE_NAME
import com.system.traffic.local.db.dao.LikeStationDao
import com.system.traffic.local.db.dao.LineDao
import com.system.traffic.local.db.dao.StationDao
import com.system.traffic.local.db.model.LikeStationLocal
import com.system.traffic.local.db.model.LineLocal
import com.system.traffic.local.db.model.StationLocal

@Database(
    entities = [StationLocal::class, LineLocal::class, LikeStationLocal::class],
    version = 2,
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