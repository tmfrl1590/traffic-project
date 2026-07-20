package com.traffic.presentation.screens.line_station.state

import com.traffic.presentation.model.LineStationItemModel

data class LineStationState(
    val isLoading: Boolean = false,
    val lineStationList: List<LineStationItemModel> = emptyList(),

    val selectedBusDirectionIndex: Int = 0,
)
