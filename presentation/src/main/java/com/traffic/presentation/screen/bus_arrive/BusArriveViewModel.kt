package com.traffic.presentation.screen.bus_arrive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.common.UIState
import com.traffic.domain.model.BusArriveModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val busArriveUseCase: com.traffic.domain.useCase.arrive.BusArriveUseCase,
): ViewModel(){

    private val _uiState = MutableStateFlow<UIState<List<BusArriveModel>>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<com.traffic.domain.model.BusArriveModel>>> = _uiState

    // 버스 도착 정보 조회
    suspend fun getBusArriveList(arsId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = busArriveUseCase.getBusArriveList(arsId)
            _uiState.value = UIState.Loading
            _uiState.value = UIState.Success(data = result)
        }
    }
}