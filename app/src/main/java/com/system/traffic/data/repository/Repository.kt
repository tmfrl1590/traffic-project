package com.system.traffic.data.repository

import com.system.traffic.data.local.db.entity.LineEntity
import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.BusArriveRepository
import com.system.traffic.domain.repository.FileRepository
import com.system.traffic.domain.repository.LineRepository
import com.system.traffic.domain.repository.StationRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val stationRepository: StationRepository,
    private val lineRepository: LineRepository,
    private val fileRepository: FileRepository,
    private val busArriveRepository: BusArriveRepository
){
    fun getSearchedStationList(keyword: String) = stationRepository.getSearchedStationList(keyword)
    fun getStationInfo(ars_id: String) = stationRepository.getStationInfo(ars_id)
    //fun getLineColor() = lineRepository.getLineColor()
    fun getFileStationData() = fileRepository.getStationFileData()
    fun insertStation(stationEntity: StationEntity) = fileRepository.insertStation(stationEntity)
    fun getLineFileData() = fileRepository.getLineFileData()
    fun insertLine(lineEntity: LineEntity) = fileRepository.insertLine(lineEntity)
    suspend fun getBusArriveList(ars_id: String) = busArriveRepository.getBusArriveList(ars_id)
}