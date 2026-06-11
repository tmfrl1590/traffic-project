package com.traffic.presentation.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.model.StationModel
import com.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.traffic.presentation.screens.home.action.HomeAction
import com.traffic.presentation.screens.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLikeStationListUseCase: GetLikeStationListUseCase,
    private val toggleLikeStationUseCase: ToggleLikeStationUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(value = HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    // 즐겨찾기 리스트 조회
    fun getLikeStationList() {
        viewModelScope.launch(Dispatchers.IO) {
            getLikeStationListUseCase().collectLatest { likeStations ->
                // 즐겨찾기 리스트의 모든 항목은 selected = true로 설정
                val updatedList = likeStations.map { it.copy(selected = true) }
                _state.update { it.copy(likeStationList = updatedList) }
            }
        }
    }

    fun toggleLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        toggleLikeStationUseCase(stationModel)
    }

    fun onAction(action: HomeAction){
        when(action){
            is HomeAction.OnClickFavoriteIcon -> toggleLikeStation(action.stationModel)
        }
    }
}