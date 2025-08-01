package com.traffic.bus_arrive

import com.traffic.bus_arrive.model.BusArriveItemModel

data class BusArriveState(
    val arriveList : List<BusArriveItemModel> = emptyList()
)
