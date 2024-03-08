package com.system.traffic.domain.repository

import com.system.traffic.domain.model.LineModel
import kotlinx.coroutines.flow.Flow

interface LineRepository {

    fun getLineColor(): Flow<List<LineModel>>

    fun getSearchedLineList(keyword: String): Flow<List<LineModel>>

     // 노선 한건 조회
     fun getLineOne(lineId: String): LineModel
}