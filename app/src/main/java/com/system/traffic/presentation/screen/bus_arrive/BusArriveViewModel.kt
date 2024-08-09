package com.system.traffic.presentation.screen.bus_arrive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.common.UIState
import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.model.BusArriveModel
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val useCase: UseCase,
): ViewModel(){

    private val _uiState = MutableStateFlow<UIState<List<BusArriveModel>>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<BusArriveModel>>> = _uiState

    // 버스 도착 정보 조회
    suspend fun getBusArriveList(arsId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.busArriveUseCase.getBusArriveList(arsId)
            _uiState.value = UIState.Loading
            println("result11: $result")
            _uiState.value = UIState.Success(data = result)
        }
    }
}