package com.system.traffic.domain.useCase.file

import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.domain.repository.FileRepository
import javax.inject.Inject

class InsertStationFileDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(stationEntity: StationEntity){
        repository.insertStation(stationEntity = stationEntity)
    }
}