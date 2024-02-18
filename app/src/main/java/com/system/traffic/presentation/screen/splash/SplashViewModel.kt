package com.system.traffic.presentation.screen.splash

import androidx.lifecycle.ViewModel
import com.system.traffic.domain.dataModel.LineEntity
import com.system.traffic.domain.dataModel.LineModel
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.domain.dataModel.StationModel
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel(){

    // json 파일로부터 정류장 정보 읽어오기
    fun getFileStationData(): List<StationModel>{
        return useCase.fileUseCase.getFileStationData()
    }

    // room 에 읽어온 파일 데이터 저장
    fun insertStationInfo(stationEntity: StationEntity){
        useCase.fileUseCase.insertStation(stationEntity)
    }

    // json 파일로부터 노선 정보 읽어오기
    fun getFileLineData(): List<LineModel>{
        return useCase.fileUseCase.getLineFileData()
    }

    fun insertLineInfo(lineEntity: LineEntity){
        useCase.fileUseCase.insertLine(lineEntity)
    }
}