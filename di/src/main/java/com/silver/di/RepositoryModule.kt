package com.silver.di

import android.content.Context
import com.traffic.data.local.dataSource.FileDataSource
import com.traffic.data.local.db.dao.LikeStationDao
import com.traffic.data.local.db.dao.LineDao
import com.traffic.data.local.db.dao.StationDao
import com.traffic.data.repository.BusArriveRepositoryImpl
import com.traffic.data.repository.DataStoreRepositoryImpl
import com.traffic.data.repository.FileRepositoryImpl
import com.traffic.data.repository.LineRepositoryImpl
import com.traffic.data.repository.StationRepositoryImpl
import com.traffic.domain.repository.BusArriveRepository
import com.traffic.domain.repository.FileRepository
import com.traffic.domain.repository.LineRepository
import com.traffic.domain.repository.StationRepository
import com.traffic.data.remote.service.TrafficService
import com.traffic.data.repository.LikeStationRepositoryImpl
import com.traffic.domain.repository.DataStoreRepository
import com.traffic.domain.repository.LikeStationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStationRepository(stationDao: StationDao): StationRepository {
        return StationRepositoryImpl(stationDao)
    }

    @Provides
    @Singleton
    fun provideLineRepository(lineDao: LineDao): LineRepository {
        return LineRepositoryImpl(lineDao)
    }

    @Provides
    @Singleton
    fun fileRepository(stationDao: StationDao, lineDao: LineDao, dataSource: FileDataSource): FileRepository {
        return FileRepositoryImpl(stationDao, lineDao, dataSource)
    }

    @Provides
    @Singleton
    fun provideBusArriveRepository(trafficApi: TrafficService): BusArriveRepository {
        return BusArriveRepositoryImpl(trafficApi)
    }

    @Provides
    @Singleton
    fun provideLikeStationRepository(likeStationDao: LikeStationDao): LikeStationRepository {
        return LikeStationRepositoryImpl(likeStationDao)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context = context)
    }
}

