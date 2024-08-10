package com.traffic.domain.useCase.file

import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class InsertLineFileDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(lineModel: LineModel){
        repository.insertLine(lineModel = lineModel)
    }
}