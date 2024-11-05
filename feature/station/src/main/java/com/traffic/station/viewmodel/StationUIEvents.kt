package com.traffic.station.viewmodel

import com.traffic.domain.model.StationModel

sealed class StationUIEvents {
    data object OnStationCardClick : StationUIEvents()
    data class OnSearchStationList(val keyword: String) : StationUIEvents()
    data class OnFavoriteIconClick(val stationModel: StationModel) : StationUIEvents()
}