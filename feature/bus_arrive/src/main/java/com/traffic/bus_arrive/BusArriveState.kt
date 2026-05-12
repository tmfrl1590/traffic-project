package com.traffic.bus_arrive

import com.traffic.bus_arrive.model.BusArriveItemModel

data class BusArriveState(
    val isLoading: Boolean = false,
    val arriveList : List<BusArriveItemModel> = emptyList()
)
