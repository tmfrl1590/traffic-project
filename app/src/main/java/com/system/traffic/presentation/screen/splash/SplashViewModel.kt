package com.system.traffic.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.data.local.db.entity.LineEntity
import com.system.traffic.domain.model.LineModel
import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.useCase.file.GetFileLineDataUseCase
import com.system.traffic.domain.useCase.file.GetFileStationDataUseCase
import com.system.traffic.domain.useCase.file.InsertLineFileDataUseCase
import com.system.traffic.domain.useCase.file.InsertStationFileDataUseCase
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

    ): ViewModel(){

    // json 파일로부터 정류장 정보 읽어오기
    suspend fun getFileStationData(): List<StationModel>{
        return getFileStationDataUseCase()
    }

    // room 에 읽어온 파일 데이터 저장
    fun insertStationInfo(stationEntity: StationEntity){
        viewModelScope.launch(Dispatchers.IO) {
            insertStationFileDataUseCase(stationEntity)
        }
    }

    // json 파일로부터 노선 정보 읽어오기
    suspend fun getFileLineData(): List<LineModel>{
        return getFileLineDataUseCase()
    }

    fun insertLineInfo(lineEntity: LineEntity){
        viewModelScope.launch(Dispatchers.IO) {
            insertLineFileDataUseCase(lineEntity)
        }
    }
}