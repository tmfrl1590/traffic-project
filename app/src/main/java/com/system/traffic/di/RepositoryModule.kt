package com.system.traffic.di

import com.system.traffic.data.local.dataSource.FileDataSource
import com.system.traffic.data.local.db.dao.LikeStationDao
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.data.local.db.dao.StationDao
import com.system.traffic.data.repository.BusArriveRepositoryImpl
import com.system.traffic.data.repository.FileRepositoryImpl
import com.system.traffic.data.repository.LineRepositoryImpl
import com.system.traffic.data.repository.StationRepositoryImpl
import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.domain.repository.FileRepository
import com.system.traffic.domain.repository.LineRepository
import com.system.traffic.domain.repository.StationRepository
import com.system.traffic.data.remote.TrafficApi
import com.system.traffic.data.repository.LikeStationRepositoryImpl
import com.system.traffic.domain.repository.LikeStationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStationRepository(stationDao: StationDao): StationRepository{
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
    fun provideBusArriveRepository(trafficApi: TrafficApi): BusArriveRepository {
        return BusArriveRepositoryImpl(trafficApi)
    }

    @Provides
    @Singleton
    fun provideLikeStationRepository(likeStationDao: LikeStationDao): LikeStationRepository {
        return LikeStationRepositoryImpl(likeStationDao)
    }
}

