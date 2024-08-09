package com.system.traffic.domain.useCase.file

import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.FileRepository
import javax.inject.Inject

class GetFileStationDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(): List<StationModel>{
        return repository.getStationFileData()
    }
}