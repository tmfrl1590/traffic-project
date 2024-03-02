package com.system.traffic.domain.repository

import com.system.traffic.data.local.db.entity.LineEntity
import com.system.traffic.domain.model.LineModel
import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.domain.model.StationModel

interface FileRepository {

    fun getStationFileData(): List<StationModel>

    fun insertStation(stationEntity: StationEntity)

    fun getLineFileData(): List<LineModel>

    fun insertLine(lineEntity: LineEntity)
}