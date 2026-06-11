package com.traffic.presentation.screens.home.state

import com.traffic.domain.model.StationModel

data class HomeState(
    val likeStationList: List<StationModel> = emptyList(),
)
