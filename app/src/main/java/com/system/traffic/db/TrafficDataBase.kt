package com.system.traffic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.system.traffic.db.dao.LineDAO
import com.system.traffic.db.dao.StationDAO
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity

@Database(entities = [StationEntity::class, LineEntity::class], version = 1)
abstract class TrafficDataBase : RoomDatabase(){

    abstract fun stationDAO() : StationDAO
    abstract fun lineDAO() : LineDAO

    companion object {

        @Volatile
        private var INSTANCE : TrafficDataBase? = null

        fun getDatabase(context : Context) : TrafficDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrafficDataBase::class.java,
                    "traffic_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
