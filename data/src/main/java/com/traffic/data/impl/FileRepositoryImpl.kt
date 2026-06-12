package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.data.model.local.toEntity
import com.traffic.domain.model.LineModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): FileRepository {
    override fun getStationFileData(): List<StationModel> {
        return localDataSource.getStationFileData().map {
            it.toDomain()
        }
    }

    override fun insertStation(stationModel: StationModel) {
        localDataSource.insertStation(stationModel.toEntity())
    }

    override fun insertStations(stations: List<StationModel>) {
        localDataSource.insertStations(stations.map { it.toEntity() })
    }

    override fun getLineFileData(): List<LineModel> {
        return localDataSource.getLineFileData().map {
            it.toDomain()
        }
    }

    override fun insertLine(lineModel: LineModel) {
        localDataSource.insertLine(lineModel.toEntity())
    }

    override fun insertLines(lines: List<LineModel>) {
        localDataSource.insertLines(lines.map { it.toEntity() })
    }
}