package com.system.traffic.presentation.screen.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {

    private val _stationInfo = MutableStateFlow(StationModel("", "", "", "", "", "", ""))
    val stationInfo : StateFlow<StationModel> = _stationInfo

    private val _searchedStationList = MutableStateFlow<List<StationModel>>(listOf())
    val searchResult : StateFlow<List<StationModel>> = _searchedStationList

    private val _likeStationList = MutableStateFlow<List<StationModel>>(listOf())
    val likeStationList : StateFlow<List<StationModel>> = _likeStationList


    // 즐겨찾기 - 리스트 가져오기 (즐겨찾기 페이지)
    //val likeStationList = useCase.stationUseCase.getLikeStationList()

    // 정류장 검색
    fun getSearchedStationList(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = useCase.stationUseCase.getSearchedStationList(keyword = "%$keyword%")

        withContext(Dispatchers.Main){
            _searchedStationList.value = result
        }
    }

    // 정류장 즐겨찾기 추가, 삭제
    /*fun updateStation(stationModel: StationModel)= viewModelScope.launch(Dispatchers.IO) {
        useCase.stationUseCase.updateStation(stationModel.copy(selected = !stationModel.selected) )
    }*/

    suspend fun getStationInfo(arsId: String){
        useCase.stationUseCase.getStationInfo(arsId).collectLatest {
            _stationInfo.emit(it)
        }
    }

    // 즐겨찾기 추가
    fun insertLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        useCase.likeStationUseCase.addLikeStation(stationModel)
    }

    // 즐겨찾기 삭제
    fun deleteLikeStation(arsId: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase.likeStationUseCase.deleteLikeStation(arsId)
    }

    // 즐겨찾기 리스트 조회
    fun getLikeStationList() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.likeStationUseCase.getLikeStationList().collectLatest {
                println("즐겨찾기 리스트 조회 : $it")
                _likeStationList.emit(it)
            }
        }
    }
}