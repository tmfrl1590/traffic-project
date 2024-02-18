package com.system.traffic.domain.repository

import com.system.traffic.domain.dataModel.LineEntity
import com.system.traffic.domain.dataModel.LineModel
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.domain.dataModel.StationModel

interface FileRepository {

    fun getStationFileData(): List<StationModel>

    fun insertStation(stationEntity: StationEntity)

    fun getLineFileData(): List<LineModel>

    fun insertLine(lineEntity: LineEntity)
}