package com.system.traffic.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.system.traffic.local.RoomConstant
import com.system.traffic.local.db.TrafficDatabase
import com.system.traffic.local.db.dao.KeywordDao
import com.system.traffic.local.db.dao.LikeStationDao
import com.system.traffic.local.db.dao.LineDao
import com.system.traffic.local.db.dao.PinnedBusDao
import com.system.traffic.local.db.dao.StationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `${RoomConstant.Table.PINNED_BUS_LOCAL}` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `busStopId` TEXT NOT NULL, 
                `lineId` TEXT NOT NULL, 
                `createdAt` INTEGER NOT NULL
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE UNIQUE INDEX IF NOT EXISTS `index_pinned_bus_entity_busStopId_lineId` 
            ON `${RoomConstant.Table.PINNED_BUS_LOCAL}` (`busStopId`, `lineId`)
            """.trimIndent()
        )
    }
}

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
        )
        .addMigrations(MIGRATION_4_5)
        .fallbackToDestructiveMigration(dropAllTables = true)
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

    @Provides
    @Singleton
    fun provideKeywordDao(database: TrafficDatabase): KeywordDao {
        return database.keywordDAO()
    }

    @Provides
    @Singleton
    fun providePinnedBusDao(database: TrafficDatabase): PinnedBusDao {
        return database.pinnedBusDAO()
    }
}