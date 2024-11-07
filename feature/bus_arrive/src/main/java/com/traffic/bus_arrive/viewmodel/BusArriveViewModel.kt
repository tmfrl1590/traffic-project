package com.traffic.bus_arrive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.common.BaseResponse
import com.traffic.common.ResultCode
import com.traffic.common.UIState
import com.traffic.domain.model.BusArriveItem
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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

    private val _busArriveListState = MutableStateFlow<UIState<BaseResponse<List<BusArriveItem>>>>(UIState.Idle)
    val busArriveListState: StateFlow<UIState<BaseResponse<List<BusArriveItem>>>> = _busArriveListState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _stationInfo = MutableStateFlow(StationModel("", "", "", "", "", "", ""))
    val stationInfo: StateFlow<StationModel> = _stationInfo

    // 버스 도착 정보 조회
    fun getBusArriveList(arsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _busArriveListState.value = UIState.Loading
            val result = busArriveUseCase(arsId)
            result.data?.map {
                it.lineKind = getLineKindUseCase(it.lineId ?: "")
            }

            when (result.code) {
                ResultCode.BUS_ARRIVE_GET_SUCCESS.code -> _busArriveListState.value = UIState.Success(data = result)
                ResultCode.BUS_ARRIVE_GET_FAILURE.code -> {
                    _busArriveListState.value = UIState.Idle
                    _errorFlow.emit(result.message)
                }
                ResultCode.COMMON_EXCEPTION.code -> {
                    _busArriveListState.value = UIState.Exception
                    _errorFlow.emit(result.message)
                }
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