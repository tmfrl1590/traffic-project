package com.silver.di

import com.traffic.data.impl.DataStoreRepositoryImpl
import com.traffic.data.impl.FileRepositoryImpl
import com.traffic.data.impl.LikeStationRepositoryImpl
import com.traffic.data.impl.LineRepositoryImpl
import com.traffic.data.impl.RemoteRepositoryImpl
import com.traffic.data.impl.StationRepositoryImpl
import com.traffic.data.local.LocalDataSource
import com.traffic.domain.repository.DataStoreRepository
import com.traffic.domain.repository.FileRepository
import com.traffic.domain.repository.LikeStationRepository
import com.traffic.domain.repository.LineRepository
import com.traffic.domain.repository.RemoteRepository
import com.traffic.domain.repository.StationRepository
import dagger.Binds
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
    fun provideStationRepository(localDataSource: LocalDataSource): StationRepository {
        return StationRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideLineRepository(): LineRepository {
        return LineRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideFileRepository(
        localDataSource: LocalDataSource
    ): FileRepository {
        return FileRepositoryImpl(localDataSource = localDataSource)
    }

    @Provides
    @Singleton
    fun provideLikeStationRepository(localDataSource: LocalDataSource): LikeStationRepository {
        return LikeStationRepositoryImpl(localDataSource = localDataSource)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(localDataSource: LocalDataSource): DataStoreRepository {
        return DataStoreRepositoryImpl(localDataSource = localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class Repository2Module {
    @Binds
    @Singleton
    abstract fun bindBusArriveRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository
}