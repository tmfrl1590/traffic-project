package com.system.traffic.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.data.local.db.dao.StationDao
import com.system.traffic.domain.dataModel.LineEntity
import com.system.traffic.domain.dataModel.StationEntity

@Database(
    entities = [StationEntity::class, LineEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrafficDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "applicationDatabase.db"
    }

    abstract fun stationDAO() : StationDao

    abstract fun lineDAO() : LineDao
}