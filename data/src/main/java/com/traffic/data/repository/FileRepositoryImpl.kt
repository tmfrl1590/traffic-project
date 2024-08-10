package com.traffic.data.repository

import com.traffic.data.local.dataSource.FileDataSource
import com.traffic.data.local.db.dao.LineDao
import com.traffic.data.local.db.dao.StationDao
import com.traffic.data.local.db.entity.toLineEntity
import com.traffic.data.local.db.entity.toStationEntity
import com.traffic.domain.model.LineModel
import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val stationDao: StationDao,
    private val lineDao: LineDao,
    private val dataSource: FileDataSource,
): FileRepository {
    override fun getStationFileData(): List<StationModel> {
        return dataSource.getStationDataFromFile()
    }

    override fun insertStation(stationModel: StationModel) {
        stationDao.insertStation(stationModel.toStationEntity())
    }

    override fun getLineFileData(): List<LineModel> {
        return dataSource.getLineDataFromFile()
    }

    override fun insertLine(lineModel: LineModel) {
        lineDao.insertLine(lineModel.toLineEntity())
    }
}