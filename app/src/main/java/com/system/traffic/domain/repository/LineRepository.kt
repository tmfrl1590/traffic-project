package com.system.traffic.domain.repository

import com.system.traffic.domain.model.LineModel

interface LineRepository {

    //fun getLineColor(): Flow<List<LineModel>>

     fun updateLine(lineModel: LineModel)

     // 노선 한건 조회
     fun getLineOne(lineId: String): LineModel
}