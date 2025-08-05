package com.traffic.bus_arrive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.domain.onSuccess
import com.traffic.bus_arrive.BusArriveState
import com.traffic.bus_arrive.model.toPresentation
import com.traffic.domain.model.StationModel
import com.traffic.domain.usecase.arrive.BusArriveUseCase
import com.traffic.domain.usecase.like.AddLikeStationUseCase
import com.traffic.domain.usecase.like.DeleteLikeStationUseCase
import com.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.traffic.domain.usecase.line.GetLineKindUseCase
import com.traffic.domain.usecase.station.GetStationInfoUseCase
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
import com.traffic.common.Resource

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

    private val _stationInfo = MutableStateFlow(
        StationModel(
            stationNum = "",
            busStopName = "정류장 정보 로딩 중...",
            nextBusStop = "",
            busStopId = "",
            arsId = "",
            longitude = "",
            latitude = "",
            selected = false
        )
    )
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
            getStationInfoUseCase(arsId), // Flow<Resource<StationModel>>
            getLikeStationListUseCase()   // Flow<List<StationModel>>
        ) { stationRes, likeStationList ->
            val likeStationSet = likeStationList.map { it.arsId }.toSet()

            when (stationRes) {
                is Resource.Success -> {
                    val stationModel = stationRes.data
                    stationModel.copy(selected = likeStationSet.contains(stationModel.arsId))
                }

                is Resource.Error -> {
                    // 에러 시 기본값 유지하되 에러 메시지 발송
                    _errorFlow.emit("정류장 정보를 불러올 수 없습니다.")
                    _stationInfo.value
                }

                is Resource.Loading -> {
                    // 로딩 중일 때는 기존 상태 유지
                    _stationInfo.value
                }

                else -> {
                    // Idle 상태일 때도 기존 상태 유지
                    _stationInfo.value
                }
            }
        }.collectLatest { updatedStation ->
            _stationInfo.emit(updatedStation)
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
                    deleteLikeStation(busArriveUIEvents.stationModel.arsId ?: "")
                } else {
                    insertLikeStation(busArriveUIEvents.stationModel)
                }
            }
        }
    }
}