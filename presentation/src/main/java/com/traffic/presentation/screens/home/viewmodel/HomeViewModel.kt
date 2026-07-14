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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getLikeStationListUseCase: GetLikeStationListUseCase,
    private val toggleLikeStationUseCase: ToggleLikeStationUseCase,
): ViewModel(){

    val state: StateFlow<HomeState> = getLikeStationListUseCase()
        .map { likeStations ->
            val updatedList = likeStations.map { it.copy(selected = true) }
            HomeState(likeStationList = updatedList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 3000),
            initialValue = HomeState()
        )

    fun toggleLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        toggleLikeStationUseCase(stationModel)
    }

    fun onAction(action: HomeAction){
        when(action){
            is HomeAction.OnClickFavoriteIcon -> toggleLikeStation(stationModel = action.stationModel)
        }
    }
}