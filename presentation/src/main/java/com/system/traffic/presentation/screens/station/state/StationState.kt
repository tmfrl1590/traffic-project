package com.system.traffic.presentation.screens.station.state

import com.system.traffic.domain.model.KeywordModel
import com.system.traffic.domain.model.StationModel

data class StationState(
    val keyword: String = "",

    val keywordList: List<KeywordModel> = emptyList(),

    val searchedStationList: List<StationModel> = emptyList(),
)