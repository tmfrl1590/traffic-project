package com.system.traffic.domain.usecase.file

import com.system.traffic.domain.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

enum class InitStep {
    LOAD_STATIONS,
    SAVE_STATIONS,
    LOAD_LINES,
    SAVE_LINES,
    DONE,
}

sealed interface InitState {
    data class Progress(val step: InitStep, val progress: Float) : InitState
    data object Complete : InitState
}

class InitializeDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    operator fun invoke(): Flow<InitState> = flow {
        emit(value = InitState.Progress(step = InitStep.LOAD_STATIONS, progress = 0.1f))
        val stationList = repository.getStationFileData()

        emit(value = InitState.Progress(step = InitStep.SAVE_STATIONS, progress = 0.3f))
        repository.insertStations(stationList)

        emit(value = InitState.Progress(step = InitStep.LOAD_LINES, progress = 0.6f))
        val lineList = repository.getLineFileData()

        emit(value = InitState.Progress(step = InitStep.SAVE_LINES, progress = 0.8f))
        repository.insertLines(lineList)

        emit(value = InitState.Progress(step = InitStep.DONE, progress = 1.0f))
        emit(value = InitState.Complete)
    }.flowOn(context = Dispatchers.IO) // 파일 읽기 등 Room 밖의 블로킹 IO 커버
}
