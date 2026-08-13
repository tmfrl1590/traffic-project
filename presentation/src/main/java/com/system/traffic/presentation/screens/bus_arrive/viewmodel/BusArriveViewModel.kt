package com.system.traffic.presentation.screens.bus_arrive.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.design.R
import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.onError
import com.system.traffic.core.domain.onSuccess
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.usecase.arrive.BusArriveUseCase
import com.system.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.system.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.system.traffic.domain.usecase.line.GetLineKindUseCase
import com.system.traffic.domain.usecase.pinned_bus.DeletePinnedBusUseCase
import com.system.traffic.domain.usecase.pinned_bus.GetPinnedBusUseCase
import com.system.traffic.domain.usecase.pinned_bus.InsertPinnedBusUseCase
import com.system.traffic.domain.usecase.station.GetStationInfoUseCase
import com.system.traffic.presentation.PresentationConstants.REFRESH_INTERVAL_SECONDS
import com.system.traffic.presentation.event.UiEvent
import com.system.traffic.presentation.event.UiEventBus
import com.system.traffic.presentation.model.toPresentation
import com.system.traffic.presentation.screens.bus_arrive.action.BusArriveAction
import com.system.traffic.presentation.screens.bus_arrive.state.BusArriveState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    private val insertPinnedBusUseCase: InsertPinnedBusUseCase,
    private val deletePinnedBusUseCase: DeletePinnedBusUseCase,
    private val getPinnedBusUseCase: GetPinnedBusUseCase,
    private val uiEventBus: UiEventBus,
): ViewModel() {

    private val _state = MutableStateFlow(value = BusArriveState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<BusArriveState> = _state
        .map { it.stationInfo.busStopId }
        .distinctUntilChanged()
        .flatMapLatest { busStopId ->
            if (busStopId.isNullOrEmpty()) {
                flowOf(emptyList())
            } else {
                getPinnedBusUseCase(busStopId = busStopId)
            }
        }
        .combine(flow = _state) { pinnedList, currentState ->
            val pinnedLineIds = pinnedList.map { it.lineId }.toSet()
            val processedList = currentState.arriveList.map { item ->
                item.copy(isPinned = pinnedLineIds.contains(item.lineId))
            }.sortedByDescending { it.isPinned }
            
            currentState.copy(arriveList = processedList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = BusArriveState()
        )

    // 자동 새로고침 카운트다운 (state와 분리해 매초 전체 state 갱신 방지)
    private val _remainingSeconds = MutableStateFlow(REFRESH_INTERVAL_SECONDS)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    // OnEnter 시 저장되는 화면 파라미터
    private var busStopId: String = ""

    fun onAction(action: BusArriveAction) {
        when (action) {
            is BusArriveAction.OnEnter -> onEnter(arsId = action.arsId, busStopId = action.busStopId)
            BusArriveAction.OnResume -> startTimer()
            BusArriveAction.OnPause -> stopTimer()
            is BusArriveAction.OnClickFavoriteIcon -> toggleLikeStation(action.stationModel)
            BusArriveAction.OnClickRefresh -> onClickRefresh()
            is BusArriveAction.OnClickPinnedIcon -> onClickPinnedIcon(lineId = action.lineId, isPinned = action.isPinned)
        }
    }

    private fun onEnter(arsId: String, busStopId: String) {
        this.busStopId = busStopId
        getStationInfo(arsId = arsId)
        getBusArriveList(busStopId = busStopId)
    }

    // 버스 도착 정보 조회
    private fun getBusArriveList(busStopId: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            busArriveUseCase(busStopId = busStopId)
                .onSuccess { result ->
                    val arriveList = result.itemList.map { it.toPresentation(lineColor = lineKindToColor(lineKind = getLineKindUseCase(lineId = it.lineId ?: ""))) }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            arriveList = arriveList,
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    when(error){
                        DataError.Remote.SERVER_TIMEOUT -> uiEventBus.sendEvent(UiEvent.ShowSnackBar(messageRes = R.string.error_server_timeout))
                        else -> uiEventBus.sendEvent(UiEvent.ShowSnackBar(messageRes = R.string.error_generic))
                    }
                }
        }
    }

    private var stationInfoJob: Job? = null

    // 정류장 정보 조회
    private fun getStationInfo(arsId: String) {
        stationInfoJob?.cancel()
        stationInfoJob = viewModelScope.launch(context = Dispatchers.IO) {
            combine(
                getStationInfoUseCase(arsId),
                getLikeStationListUseCase()
            ) { stationRes, likeStationList ->
                // combine 블록은 순수 변환만 담당
                val likeStationSet = likeStationList.mapTo(HashSet()) { it.arsId }
                stationRes to likeStationSet
            }
                .catch { uiEventBus.sendEvent(UiEvent.ShowSnackBar(messageRes = R.string.error_generic)) } // repository에서 흘려보낸 예외 처리
                .collectLatest { (stationRes, likeStationSet) -> // 구조분해
                    val updatedStation = stationRes.copy(
                        selected = stationRes.arsId in likeStationSet
                    )
                    _state.update { it.copy(stationInfo = updatedStation) }
                }
        }
    }

    private fun toggleLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        toggleLikeStationUseCase(stationModel)
    }


    private var timerJob: Job? = null

    // 자동 새로고침 타이머 시작 (카운트다운)
    private fun startTimer() {
        if (busStopId.isEmpty()) return

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _remainingSeconds.value = REFRESH_INTERVAL_SECONDS
            while (true) {
                delay(1000L)
                val currentSeconds = _remainingSeconds.value
                if (currentSeconds > 1) {
                    _remainingSeconds.value = currentSeconds - 1
                } else {
                    _remainingSeconds.value = REFRESH_INTERVAL_SECONDS
                    getBusArriveList(busStopId = busStopId)
                }
            }
        }
    }

    // 타이머 정지 및 상태 리셋
    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _remainingSeconds.value = REFRESH_INTERVAL_SECONDS
    }

    // 도착정보 새로고침
    private fun onClickRefresh(){
        if (busStopId.isNotEmpty()) {
            startTimer()
            getBusArriveList(busStopId = busStopId)
        }
    }

    // 핀 아이콘 클릭
    private fun onClickPinnedIcon(lineId: String, isPinned: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            if(isPinned){
                deletePinnedBusUseCase(busStopId = busStopId, lineId = lineId)
            } else {
                insertPinnedBusUseCase(busStopId = busStopId, lineId = lineId)
            }
        }
    }

    fun lineKindToColor(lineKind: String): Color {
        return when (lineKind) {
            "1" -> Color(0xFFDC2626)
            "2" -> Color(0xFF16A34A)
            "3" -> Color(0xFF2563EB)
            else -> Color.Black
        }
    }
}