package com.system.traffic.domain.repository

import com.system.traffic.domain.model.LineModel

interface LineRepository {

    //fun getLineColor(): Flow<List<LineModel>>

     fun updateLine(lineModel: LineModel)
}