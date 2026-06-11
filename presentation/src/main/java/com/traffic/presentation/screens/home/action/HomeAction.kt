package com.traffic.presentation.screens.home.action

import com.traffic.domain.model.StationModel

sealed interface HomeAction {
    data class OnClickFavoriteIcon(val stationModel: StationModel) : HomeAction
}