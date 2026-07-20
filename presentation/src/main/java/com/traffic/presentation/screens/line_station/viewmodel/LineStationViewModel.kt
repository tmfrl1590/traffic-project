package com.traffic.presentation.screens.line_station.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.domain.onError
import com.system.traffic.core.domain.onSuccess
import com.traffic.domain.usecase.line.GetLineStationListUseCase
import com.traffic.presentation.model.toPresentation
import com.traffic.presentation.screens.line_station.action.LineStationAction
import com.traffic.presentation.screens.line_station.state.LineStationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineStationViewModel @Inject constructor(
    private val getLineStationListUseCase: GetLineStationListUseCase
): ViewModel(){

    private val _state = MutableStateFlow(value = LineStationState())
    val state: StateFlow<LineStationState> = _state.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    // 노선 경유지 조회
    fun getLineStationList(lineId: String){
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getLineStationListUseCase(lineId = lineId)
                .onSuccess { result ->
                    val lineStationList = result.items.map { it.toPresentation() }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            lineStationList = lineStationList,
                        )
                    }
                }
                .onError {
                    _errorFlow.emit("오류가 발생하였습니다.")
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    fun selectTab(
        selectedBusDirectionIndex: Int,
    ){
        _state.update { it.copy(selectedBusDirectionIndex = selectedBusDirectionIndex) }
    }

    fun onAction(action: LineStationAction){
        when(action){
            is LineStationAction.OnClickBusDirectionTab -> selectTab(selectedBusDirectionIndex = action.selectedBusDirectionIndex)
        }
    }
}