package com.traffic.data.di

import com.traffic.data.impl.DataStoreRepositoryImpl
import com.traffic.data.impl.FileRepositoryImpl
import com.traffic.data.impl.KeywordRepositoryImpl
import com.traffic.data.impl.LikeStationRepositoryImpl
import com.traffic.data.impl.LineRepositoryImpl
import com.traffic.data.impl.NetworkRepositoryImpl
import com.traffic.data.impl.PinnedBusRepositoryImpl
import com.traffic.data.impl.RemoteRepositoryImpl
import com.traffic.data.impl.StationRepositoryImpl
import com.traffic.domain.repository.DataStoreRepository
import com.traffic.domain.repository.FileRepository
import com.traffic.domain.repository.KeywordRepository
import com.traffic.domain.repository.LikeStationRepository
import com.traffic.domain.repository.LineRepository
import com.traffic.domain.repository.NetworkRepository
import com.traffic.domain.repository.PinnedBusRepository
import com.traffic.domain.repository.RemoteRepository
import com.traffic.domain.repository.StationRepository
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