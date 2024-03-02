package com.system.traffic.di

import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.domain.repository.FileRepository
import com.system.traffic.domain.repository.LikeLineRepository
import com.system.traffic.domain.repository.LikeStationRepository
import com.system.traffic.domain.repository.LineRepository
import com.system.traffic.domain.repository.StationRepository
import com.system.traffic.domain.useCase.BusArriveUseCase
import com.system.traffic.domain.useCase.FileUseCase
import com.system.traffic.domain.useCase.LikeLineUseCase
import com.system.traffic.domain.useCase.LikeStationUseCase
import com.system.traffic.domain.useCase.LineUseCase
import com.system.traffic.domain.useCase.StationUseCase
import com.system.traffic.domain.useCase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCases(
        stationRepository: StationRepository,
        lineRepository: LineRepository,
        busArriveRepository: BusArriveRepository,
        fileRepository: FileRepository,
        likeStationRepository: LikeStationRepository,
        likeLineRepository: LikeLineRepository,
    ): UseCase {
        return UseCase(
            stationUseCase = StationUseCase(stationRepository),
            lineUseCase = LineUseCase(lineRepository),
            busArriveUseCase = BusArriveUseCase(busArriveRepository),
            fileUseCase = FileUseCase(fileRepository),
            likeStationUseCase = LikeStationUseCase(likeStationRepository),
            likeLineUseCase = LikeLineUseCase(likeLineRepository),
        )
    }
}