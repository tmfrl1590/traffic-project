package com.system.traffic.di

import android.content.Context
import androidx.room.Room
import com.system.traffic.data.local.db.TrafficDatabase
import com.system.traffic.data.local.db.dao.LikeLineDao
import com.system.traffic.data.local.db.dao.LikeStationDao
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.data.local.db.dao.StationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TrafficDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TrafficDatabase::class.java,
            TrafficDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun stationDao(database: TrafficDatabase): StationDao {
        return database.stationDAO()
    }

    @Provides
    @Singleton
    fun lineDao(database: TrafficDatabase): LineDao {
        return database.lineDAO()
    }

    @Provides
    @Singleton
    fun likeStationDao(database: TrafficDatabase): LikeStationDao {
        return database.likeStationDAO()
    }

    @Provides
    @Singleton
    fun likeLineDao(database: TrafficDatabase): LikeLineDao {
        return database.likeLineDAO()
    }
}