package com.traffic.domain.repository

import com.traffic.domain.model.LineModel
import com.traffic.domain.model.StationModel

interface FileRepository {

    fun getStationFileData(): List<StationModel>

    fun insertStation(stationModel: StationModel)

    fun insertStations(stations: List<StationModel>)

    fun getLineFileData(): List<LineModel>

    fun insertLine(lineModel: LineModel)

    fun insertLines(lines: List<LineModel>)
}