package com.system.traffic.domain.useCase

data class UseCase(
    val stationUseCase: StationUseCase,
    val lineUseCase: LineUseCase,
    val busArriveUseCase: BusArriveUseCase,
    val fileUseCase: FileUseCase,
    val likeStationUseCase: LikeStationUseCase,
    val likeLineUseCase: LikeLineUseCase,
)
