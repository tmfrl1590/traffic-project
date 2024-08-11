package com.traffic.presentation.screen.bus_arrive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.common.BaseResponse
import com.traffic.common.ResultCode
import com.traffic.common.UIState
import com.traffic.domain.model.BusArriveModel
import com.traffic.domain.useCase.arrive.BusArriveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val busArriveUseCase: BusArriveUseCase,
): ViewModel() {

    private val _busArriveListState = MutableStateFlow<UIState<BaseResponse<List<BusArriveModel>>>>(UIState.Idle)
    val busArriveListState: StateFlow<UIState<BaseResponse<List<BusArriveModel>>>> = _busArriveListState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    // 버스 도착 정보 조회
    fun getBusArriveList(arsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _busArriveListState.value = UIState.Loading
            val result = busArriveUseCase(arsId)

            when (result.code) {
                ResultCode.BUS_ARRIVE_GET_SUCCESS.code -> _busArriveListState.value = UIState.Success(data = result)
                ResultCode.BUS_ARRIVE_GET_FAILURE.code -> {
                    _busArriveListState.value = UIState.Idle
                    _errorFlow.emit(result.message)
                }
                ResultCode.COMMON_EXCEPTION.code -> {
                    _busArriveListState.value = UIState.Idle
                    _errorFlow.emit(result.message)
                }
            }
        }
    }
}