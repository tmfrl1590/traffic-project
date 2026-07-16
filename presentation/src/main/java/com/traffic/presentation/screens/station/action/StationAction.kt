package com.traffic.presentation.screens.station.action

import com.traffic.domain.model.StationModel

sealed interface StationAction {
    data class OnInputKeyword(val keyword: String): StationAction
    data object OnSearchStation: StationAction
    data class OnClickFavoriteIcon(val stationModel: StationModel) : StationAction
    data class OnClickKeyword(val keyword: String): StationAction
    data class OnDeleteKeyword(val keyword: String): StationAction
    data object OnAllDeleteKeywordList: StationAction
    data object OnDeleteInputText: StationAction
}
