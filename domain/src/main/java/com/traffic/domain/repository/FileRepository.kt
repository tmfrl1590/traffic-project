package com.traffic.domain.repository

import com.traffic.domain.model.LineModel
import com.traffic.domain.model.StationModel

interface FileRepository {

    fun getStationFileData(): List<StationModel>

    suspend fun insertStation(stationModel: StationModel)

    suspend fun insertStations(stations: List<StationModel>)

    fun getLineFileData(): List<LineModel>

    suspend fun insertLine(lineModel: LineModel)

    suspend fun insertLines(lines: List<LineModel>)
}