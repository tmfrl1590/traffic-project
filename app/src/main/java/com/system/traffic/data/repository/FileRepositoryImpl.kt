package com.system.traffic.data.repository

import com.system.traffic.data.local.dataSource.FileDataSource
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.data.local.db.dao.StationDao
import com.system.traffic.data.local.db.entity.LineEntity
import com.system.traffic.domain.model.LineModel
import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.FileRepository

class FileRepositoryImpl (
    private val stationDao: StationDao,
    private val lineDao: LineDao,
    private val dataSource: FileDataSource,
): FileRepository{
    override fun getStationFileData(): List<StationModel> {
        return dataSource.getStationDataFromFile()
    }

    override fun insertStation(stationEntity: StationEntity) {
        stationDao.insertStation(stationEntity)
    }

    override fun getLineFileData(): List<LineModel> {
        return dataSource.getLineDataFromFile()
    }

    override fun insertLine(lineEntity: LineEntity) {
        lineDao.insertLine(lineEntity)
    }
}