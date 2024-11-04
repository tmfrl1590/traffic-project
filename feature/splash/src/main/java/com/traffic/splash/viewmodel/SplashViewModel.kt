package com.traffic.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.model.LineModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.useCase.datastore.GetIsFirstLoginUseCase
import com.traffic.domain.useCase.datastore.SetUpIsFirstLoginUseCase
import com.traffic.domain.useCase.file.GetFileLineDataUseCase
import com.traffic.domain.useCase.file.GetFileStationDataUseCase
import com.traffic.domain.useCase.file.InsertLineFileDataUseCase
import com.traffic.domain.useCase.file.InsertStationFileDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertStationFileDataUseCase: InsertStationFileDataUseCase,
    private val insertLineFileDataUseCase: InsertLineFileDataUseCase,
    private val getFileStationDataUseCase: GetFileStationDataUseCase,
    private val getFileLineDataUseCase: GetFileLineDataUseCase,
    private val setUpIsFirstLoginUseCase: SetUpIsFirstLoginUseCase,
    private val getIsFirstLoginUseCase: GetIsFirstLoginUseCase,
    ): ViewModel(){

    // json 파일로부터 정류장 정보 읽어오기
    suspend fun getFileStationData(): List<StationModel>{
        return getFileStationDataUseCase()
    }

    // room 에 읽어온 파일 데이터 저장
    fun insertStationInfo(stationModel: StationModel){
        viewModelScope.launch(Dispatchers.IO) {
            insertStationFileDataUseCase(stationModel = stationModel)
        }
    }

    // json 파일로부터 노선 정보 읽어오기
    suspend fun getFileLineData(): List<LineModel>{
        return getFileLineDataUseCase()
    }

    fun insertLineInfo(lineModel: LineModel){
        viewModelScope.launch(Dispatchers.IO) {
            insertLineFileDataUseCase(lineModel = lineModel)
        }
    }

    fun setUpIsFirstLogin() = viewModelScope.launch(Dispatchers.IO) {
        setUpIsFirstLoginUseCase()
    }

    suspend fun getIsFirstLogin(): Boolean{
        return getIsFirstLoginUseCase()
    }
}