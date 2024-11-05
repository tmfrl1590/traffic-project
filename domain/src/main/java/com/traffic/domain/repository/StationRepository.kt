package com.traffic.domain.repository

import com.traffic.common.Resource
import com.traffic.domain.model.StationModel
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    fun getSearchedStationList(keyword: String): Flow<Resource<List<StationModel>>>


    fun getStationInfo(arsId: String): Flow<StationModel>

}