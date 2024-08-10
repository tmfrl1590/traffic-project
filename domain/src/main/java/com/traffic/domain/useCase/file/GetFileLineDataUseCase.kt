package com.traffic.domain.useCase.file

import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.FileRepository
import javax.inject.Inject

class GetFileLineDataUseCase @Inject constructor(
    private val repository: com.traffic.domain.repository.FileRepository
) {
    suspend operator fun invoke(): List<com.traffic.domain.model.LineModel>{
        return repository.getLineFileData()
    }
}