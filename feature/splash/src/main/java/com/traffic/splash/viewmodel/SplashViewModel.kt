package com.traffic.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.model.LineModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.usecase.datastore.GetIsFirstLoginUseCase
import com.traffic.domain.usecase.datastore.SetUpIsFirstLoginUseCase
import com.traffic.domain.usecase.file.GetFileLineDataUseCase
import com.traffic.domain.usecase.file.GetFileStationDataUseCase
import com.traffic.domain.usecase.file.InsertLineFileDataUseCase
import com.traffic.domain.usecase.file.InsertStationFileDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DataLoadingState(
    val isLoading: Boolean = false,
    val message: String = "",
    val progress: Float = 0f
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertStationFileDataUseCase: InsertStationFileDataUseCase,
    private val insertLineFileDataUseCase: InsertLineFileDataUseCase,
    private val getFileStationDataUseCase: GetFileStationDataUseCase,
    private val getFileLineDataUseCase: GetFileLineDataUseCase,
    private val setUpIsFirstLoginUseCase: SetUpIsFirstLoginUseCase,
    private val getIsFirstLoginUseCase: GetIsFirstLoginUseCase,
) : ViewModel() {

    private val _loadingState = MutableStateFlow(DataLoadingState())
    val loadingState: StateFlow<DataLoadingState> = _loadingState

    // json 파일로부터 정류장 정보 읽어오기
    suspend fun getFileStationData(): List<StationModel>{
        return getFileStationDataUseCase()
    }

    // room 에 읽어온 파일 데이터 저장
    suspend fun insertStationInfo(stationModel: StationModel) {
        insertStationFileDataUseCase(stationModel = stationModel)
    }

    // json 파일로부터 노선 정보 읽어오기
    suspend fun getFileLineData(): List<LineModel>{
        return getFileLineDataUseCase()
    }

    suspend fun insertLineInfo(lineModel: LineModel) {
        insertLineFileDataUseCase(lineModel = lineModel)
    }

    fun updateLoadingState(message: String, progress: Float) {
        _loadingState.value = DataLoadingState(
            isLoading = true,
            message = message,
            progress = progress
        )
    }

    fun completeLoading() {
        _loadingState.value = DataLoadingState(
            isLoading = false,
            message = "완료",
            progress = 1.0f
        )
    }

    fun setUpIsFirstLogin() = viewModelScope.launch(Dispatchers.IO) {
        setUpIsFirstLoginUseCase()
    }

    suspend fun getIsFirstLogin(): Boolean{
        return getIsFirstLoginUseCase()
    }
}