package com.system.traffic.domain.useCase.file

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.FileRepository
import javax.inject.Inject

class GetFileLineDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(): List<LineModel>{
        return repository.getLineFileData()
    }
}