package com.system.traffic.presentation.screen.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.useCase.like.AddLikeStationUseCase
import com.system.traffic.domain.useCase.like.DeleteLikeStationUseCase
import com.system.traffic.domain.useCase.like.GetLikeStationListUseCase
import com.system.traffic.domain.useCase.station.GetSearchStationUseCase
import com.system.traffic.domain.useCase.station.GetStationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getSearchStationUseCase: GetSearchStationUseCase,
    private val getStationInfoUseCase: GetStationInfoUseCase,
    private val addLikeStationUseCase: AddLikeStationUseCase,
    private val deleteLikeStationUseCase: DeleteLikeStationUseCase,
    private val getLikeStationListUseCase: GetLikeStationListUseCase,
): ViewModel() {

    private val _stationInfo = MutableStateFlow(StationModel("", "", "", "", "", "", ""))
    val stationInfo : StateFlow<StationModel> = _stationInfo

    private val _searchedStationList = MutableStateFlow<List<StationModel>>(listOf())
    val searchResult : StateFlow<List<StationModel>> = _searchedStationList

    private val _likeStationList = MutableStateFlow<List<StationModel>>(listOf())
    val likeStationList : StateFlow<List<StationModel>> = _likeStationList


    // 정류장 검색
    fun getSearchedStationList(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        getSearchStationUseCase(keyword = "%$keyword%").collectLatest {
            _searchedStationList.emit(it)
        }
    }

    fun getStationInfo(arsId: String){
        viewModelScope.launch (Dispatchers.IO){
            getStationInfoUseCase(arsId).collectLatest {
                _stationInfo.emit(it)
            }
        }
    }

    // 즐겨찾기 추가
    fun insertLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        addLikeStationUseCase(stationModel)
    }

    // 즐겨찾기 삭제
    fun deleteLikeStation(arsId: String) = viewModelScope.launch(Dispatchers.IO) {
        deleteLikeStationUseCase(arsId)
    }

    // 즐겨찾기 리스트 조회
    fun getLikeStationList() {
        viewModelScope.launch(Dispatchers.IO) {
            getLikeStationListUseCase().collectLatest {
                _likeStationList.emit(it)
            }
        }
    }
}