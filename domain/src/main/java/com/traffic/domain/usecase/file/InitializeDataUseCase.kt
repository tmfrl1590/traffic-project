package com.traffic.domain.usecase.file

import com.traffic.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed interface InitState {
    data class Progress(val message: String, val progress: Float) : InitState
    data object Complete : InitState
}

class InitializeDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    operator fun invoke(): Flow<InitState> = flow {
        emit(InitState.Progress("정류장 정보를 불러오는 중...", 0.1f))
        val stationList = repository.getStationFileData()
        
        emit(InitState.Progress("정류장 정보를 저장하는 중...", 0.3f))
        repository.insertStations(stationList)
        
        emit(InitState.Progress("노선 정보를 불러오는 중...", 0.6f))
        val lineList = repository.getLineFileData()
        
        emit(InitState.Progress("노선 정보를 저장하는 중...", 0.8f))
        repository.insertLines(lineList)
        
        emit(InitState.Progress("초기화 완료", 1.0f))
        emit(InitState.Complete)
    }
}
