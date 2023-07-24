package com.system.traffic.main.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.system.traffic.dataModel.StationModel
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class LikeViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    //lateinit var likeStationList : LiveData<List<StationEntity>>
    //lateinit var likeLineList : LiveData<List<LineEntity>>




    // 정류장 - 즐겨찾기 목록 가져오기
    /*fun getLikeStationList() = viewModelScope.launch {
        likeStationList = dbRepository.getLikeStationList().asLiveData()
    }*/

    val likeStationList = dbRepository.getLikeStationList()
        .stateIn(
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000),
            scope = viewModelScope
        )



    // 버스 - 즐겨찾기 목록 가져오기
    /*fun getLikeLineList() = viewModelScope.launch {
        likeLineList = dbRepository.getLikeLineList().asLiveData()
    }*/

    val likeLineList = dbRepository.getLikeLineList()
        .stateIn(
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000),
            scope = viewModelScope
        )

    // 정류장 - 즐겨찾기 추가, 삭제
    fun updateStation(stationEntity: StationEntity) = viewModelScope.launch(Dispatchers.IO) {
        if(stationEntity.selected == "0"){
            stationEntity.selected = "1"
        }else{
            stationEntity.selected = "0"
        }
        dbRepository.updateStation(stationEntity)
    }

    // 버스- 즐겨찾기 추가, 삭제
    fun updateLine(lineEntity: LineEntity) = viewModelScope.launch(Dispatchers.IO) {
        if(lineEntity.selected == "0"){
            lineEntity.selected = "1"
        }else{
            lineEntity.selected = "0"
        }
        dbRepository.updateLine(lineEntity)
    }

    // 즐겨찾기 - 정류장 추가 삭제(상세화면에서)
    fun updateStationLike(ars_id : String) = viewModelScope.launch(Dispatchers.IO) {
        val result : StationEntity = dbRepository.getIsLikeStation(ars_id)

        if(result == null){
            dbRepository.updateStationLike("1", ars_id)
        }else{
            dbRepository.updateStationLike("0",ars_id)
        }
    }

    // 즐겨찾기 - 버스 추가 삭제(상세화면에서)
    fun updateLineLike(lineId: String) = viewModelScope.launch(Dispatchers.IO) {
        val result : LineEntity = dbRepository.getIsLikeLine(lineId)
        if(result == null){
            dbRepository.updateLineLike("1",lineId)
        }else{
            dbRepository.updateLineLike("0", lineId)
        }
    }
}