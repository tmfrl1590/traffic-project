package com.traffic.domain.usecase.file

import com.traffic.domain.model.StationModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class GetFileStationDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(): List<StationModel>{
        return repository.getStationFileData()
    }
}