package com.system.traffic.data.di

import com.system.traffic.data.impl.DataStoreRepositoryImpl
import com.system.traffic.data.impl.FileRepositoryImpl
import com.system.traffic.data.impl.KeywordRepositoryImpl
import com.system.traffic.data.impl.LikeStationRepositoryImpl
import com.system.traffic.data.impl.LineRepositoryImpl
import com.system.traffic.data.impl.NetworkRepositoryImpl
import com.system.traffic.data.impl.PinnedBusRepositoryImpl
import com.system.traffic.data.impl.RemoteRepositoryImpl
import com.system.traffic.data.impl.StationRepositoryImpl
import com.system.traffic.domain.repository.DataStoreRepository
import com.system.traffic.domain.repository.FileRepository
import com.system.traffic.domain.repository.KeywordRepository
import com.system.traffic.domain.repository.LikeStationRepository
import com.system.traffic.domain.repository.LineRepository
import com.system.traffic.domain.repository.NetworkRepository
import com.system.traffic.domain.repository.PinnedBusRepository
import com.system.traffic.domain.repository.RemoteRepository
import com.system.traffic.domain.repository.StationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBusArriveRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository

    @Binds
    @Singleton
    abstract fun bindStationRepository(stationRepositoryImpl: StationRepositoryImpl): StationRepository

    @Binds
    @Singleton
    abstract fun bindLineRepository(lineRepositoryImpl: LineRepositoryImpl): LineRepository

    @Binds
    @Singleton
    abstract fun bindLikeStationRepository(likeStationRepositoryImpl: LikeStationRepositoryImpl): LikeStationRepository

    @Binds
    @Singleton
    abstract fun bindKeywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository

    @Binds
    @Singleton
    abstract fun bindPinnedBusRepository(pinnedBusRepositoryImpl: PinnedBusRepositoryImpl): PinnedBusRepository

    @Binds
    @Singleton
    abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository
}