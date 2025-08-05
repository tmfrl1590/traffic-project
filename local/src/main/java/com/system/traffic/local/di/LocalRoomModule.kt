package com.system.traffic.local.di

import android.content.Context
import androidx.room.Room
import com.system.traffic.local.TrafficDatabase
import com.system.traffic.local.db.dao.LikeStationDao
import com.system.traffic.local.db.dao.LineDao
import com.system.traffic.local.db.dao.StationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
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
    fun provideStationDao(database: TrafficDatabase): StationDao {
        return database.stationDAO()
    }

    @Provides
    @Singleton
    fun provideLineDao(database: TrafficDatabase): LineDao {
        return database.lineDAO()
    }

    @Provides
    @Singleton
    fun provideLikeStationDao(database: TrafficDatabase): LikeStationDao {
        return database.likeStationDAO()
    }
}