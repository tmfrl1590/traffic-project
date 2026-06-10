package com.traffic.presentation.screens.bus_arrive

import com.traffic.presentation.screens.bus_arrive.model.BusArriveItemModel

data class BusArriveState(
    val isLoading: Boolean = false,
    val arriveList : List<BusArriveItemModel> = emptyList()
)
