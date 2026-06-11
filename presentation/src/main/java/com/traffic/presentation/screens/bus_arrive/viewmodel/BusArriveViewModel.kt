package com.traffic.presentation.screens.bus_arrive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.domain.onError
import com.system.traffic.core.domain.onSuccess
import com.traffic.common.Resource
import com.traffic.common.lineKindToColor
import com.traffic.domain.model.StationModel
import com.traffic.domain.usecase.arrive.BusArriveUseCase
import com.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.traffic.domain.usecase.line.GetLineKindUseCase
import com.traffic.domain.usecase.station.GetStationInfoUseCase
import com.traffic.presentation.screens.bus_arrive.action.BusArriveAction
import com.traffic.presentation.screens.bus_arrive.model.toPresentation
import com.traffic.presentation.screens.bus_arrive.state.BusArriveState
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
    private val toggleLikeStationUseCase: ToggleLikeStationUseCase,
): ViewModel() {
    
    private val _state = MutableStateFlow(value = BusArriveState())
    val state: StateFlow<BusArriveState> = _state.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    // 버스 도착 정보 조회
    fun getBusArriveList(arsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            busArriveUseCase(arsId)
                .onSuccess { result ->
                    val arriveList = result.itemList.map { it.toPresentation(lineColor = lineKindToColor(lineKind = getLineKindUseCase(lineId = it.lineId ?: ""))) }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            arriveList = arriveList,
                        )
                    }
                }
                .onError { _state.update { it.copy(isLoading = false) } }
        }
    }

    // 정류장 정보 조회
    fun getStationInfo(arsId: String) {
        viewModelScope.launch {
            combine(
                getStationInfoUseCase(arsId),   // Flow<Resource<StationModel>>
                getLikeStationListUseCase()      // Flow<List<StationModel>>
            ) { stationRes, likeStationList ->
                // combine 블록은 순수 변환만 담당
                val likeStationSet = likeStationList.mapTo(HashSet()) { it.arsId }
                stationRes to likeStationSet
            }
                .collectLatest { (stationRes, likeStationSet) -> // 구조분해
                    when (stationRes) {
                        is Resource.Success -> {
                            val updatedStation = stationRes.data.copy(
                                selected = stationRes.data.arsId in likeStationSet
                            )
                            _state.update { it.copy(stationInfo = updatedStation) }
                        }
                        is Resource.Error -> _errorFlow.emit("정류장 정보를 불러올 수 없습니다.")
                        is Resource.Loading -> Unit
                        is Resource.Idle -> Unit
                    }
                }
        }
    }

    fun toggleLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        toggleLikeStationUseCase(stationModel)
    }


    fun onAction(action: BusArriveAction){
        when(action){
            is BusArriveAction.OnFavoriteIconClick -> toggleLikeStation(action.stationModel)
        }
    }
}