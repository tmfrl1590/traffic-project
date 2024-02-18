package com.system.traffic.domain.useCase

import com.system.traffic.data.repository.Repository
import com.system.traffic.domain.dataModel.LineEntity
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.domain.repository.FileRepository

class FileUseCase(
    //private val repository: Repository
    private val repository: FileRepository
) {

    fun getFileStationData() = repository.getStationFileData()

    fun insertStation(stationEntity: StationEntity) = repository.insertStation(stationEntity)

    fun getLineFileData() = repository.getLineFileData()

    fun insertLine(lineEntity: LineEntity) = repository.insertLine(lineEntity)
}