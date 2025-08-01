package com.traffic.bus_arrive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.domain.onSuccess
import com.traffic.bus_arrive.BusArriveState
import com.traffic.bus_arrive.model.toPresentation
import com.traffic.domain.model.StationModel
import com.traffic.domain.useCase.arrive.BusArriveUseCase
import com.traffic.domain.useCase.like.AddLikeStationUseCase
import com.traffic.domain.useCase.like.DeleteLikeStationUseCase
import com.traffic.domain.useCase.like.GetLikeStationListUseCase
import com.traffic.domain.useCase.line.GetLineKindUseCase
import com.traffic.domain.useCase.station.GetStationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val busArriveUseCase: BusArriveUseCase,
    private val getLineKindUseCase: GetLineKindUseCase,
    private val getStationInfoUseCase: GetStationInfoUseCase,
    private val getLikeStationListUseCase: GetLikeStationListUseCase,
    private val addLikeStationUseCase: AddLikeStationUseCase,
    private val deleteLikeStationUseCase: DeleteLikeStationUseCase,
): ViewModel() {
    
    private val _state = MutableStateFlow(BusArriveState())
    val state: StateFlow<BusArriveState> = _state.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _stationInfo = MutableStateFlow(StationModel("", "", "", "", "", "", ""))
    val stationInfo: StateFlow<StationModel> = _stationInfo

    // 버스 도착 정보 조회
    fun getBusArriveList(arsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            busArriveUseCase(arsId)
                .onSuccess { result ->
                    val arriveList = result.itemList.map { it.toPresentation() }
                    arriveList.map { it.lineKind = getLineKindUseCase(it.lineId ?: "")} // 색상 설정 추후에 수정 필요
                    _state.update { it.copy(arriveList = arriveList) }
                }
        }
    }

    fun getStationInfo(arsId: String) = viewModelScope.launch(Dispatchers.IO) {
        combine(
            getStationInfoUseCase(arsId),
            getLikeStationListUseCase()
        ) { stationInfo, likeStationList ->
            val likeStationSet = likeStationList.map { it.arsId }.toSet()
            stationInfo.copy(selected = likeStationSet.contains(stationInfo.arsId))
        }.collectLatest {
            _stationInfo.emit(it)
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

    fun onBusArriveUIEvents(busArriveUIEvents: BusArriveUIEvents){
        when(busArriveUIEvents){
            is BusArriveUIEvents.OnFavoriteIconClick -> {
                if(busArriveUIEvents.stationModel.selected){
                    deleteLikeStation(busArriveUIEvents.stationModel.busStopId ?: "")
                } else {
                    insertLikeStation(busArriveUIEvents.stationModel)
                }
            }
        }
    }
}