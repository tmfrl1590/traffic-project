package com.traffic.domain.useCase.file

import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class InsertStationFileDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(stationModel: StationModel){
        repository.insertStation(stationModel = stationModel)
    }
}