package com.system.traffic.presentation.screens.home.action

import com.system.traffic.domain.model.StationModel

sealed interface HomeAction {
    data class OnClickFavoriteIcon(val stationModel: StationModel) : HomeAction
}