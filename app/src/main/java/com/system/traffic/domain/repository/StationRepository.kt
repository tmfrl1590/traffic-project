package com.system.traffic.domain.repository

import com.system.traffic.domain.model.StationModel
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    fun getSearchedStationList(keyword: String): List<StationModel>


    fun getStationInfo(arsId: String): Flow<StationModel>

}