package com.traffic.presentation.screens.station.state

import com.traffic.domain.model.KeywordModel
import com.traffic.domain.model.StationModel

data class StationState(
    val keyword: String = "",

    val keywordList: List<KeywordModel> = emptyList(),

    val searchedStationList: List<StationModel> = emptyList(),
)