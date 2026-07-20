package com.traffic.presentation.screens.line_station.action

sealed interface LineStationAction {
    data class OnClickBusDirectionTab(val selectedBusDirectionIndex: Int): LineStationAction
}