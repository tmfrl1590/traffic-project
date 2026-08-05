package com.system.traffic.presentation.screens.home.state

import com.system.traffic.domain.model.StationModel

data class HomeState(
    val likeStationList: List<StationModel> = emptyList(),
)
