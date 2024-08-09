package com.system.traffic.domain.useCase.file

import com.system.traffic.data.local.db.entity.LineEntity
import com.system.traffic.domain.repository.FileRepository
import javax.inject.Inject

class InsertLineFileDataUseCase @Inject constructor(
    private val repository: FileRepository
) {
    suspend operator fun invoke(lineEntity: LineEntity){
        repository.insertLine(lineEntity = lineEntity)
    }
}