package com.system.traffic.data.impl

import com.system.traffic.data.local.LocalDataSource
import com.system.traffic.data.model.local.toEntity
import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): FileRepository {
    override fun getStationFileData(): List<StationModel> {
        return localDataSource.getStationFileData().map {
            it.toDomain()
        }
    }

    override suspend fun insertStation(stationModel: StationModel) {
        localDataSource.insertStation(stationModel.toEntity())
    }

    override suspend fun insertStations(stations: List<StationModel>) {
        localDataSource.insertStations(stations.map { it.toEntity() })
    }

    override fun getLineFileData(): List<LineModel> {
        return localDataSource.getLineFileData().map {
            it.toDomain()
        }
    }

    override suspend fun insertLine(lineModel: LineModel) {
        localDataSource.insertLine(lineModel.toEntity())
    }

    override suspend fun insertLines(lines: List<LineModel>) {
        localDataSource.insertLines(lines.map { it.toEntity() })
    }
}