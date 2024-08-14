package com.traffic.domain.repository

import com.traffic.domain.model.LineModel
import kotlinx.coroutines.flow.Flow

interface LineRepository {

    fun getSearchedLineList(keyword: String): Flow<List<LineModel>>

     // 노선 한건 조회
     fun getLineOne(lineId: String): LineModel

     // 노선 LineKind 조회
     fun getLineKind(lineId: String): String
}