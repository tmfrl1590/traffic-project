package com.traffic.domain.useCase.file

import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class GetFileLineDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(): List<LineModel>{
        return repository.getLineFileData()
    }
}