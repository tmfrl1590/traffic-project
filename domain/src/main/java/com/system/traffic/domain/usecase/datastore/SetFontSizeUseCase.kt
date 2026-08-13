package com.system.traffic.domain.usecase.datastore

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetFontSizeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(fontSize: AppFontSize) {
        dataStoreRepository.setAppFontSize(fontSize = fontSize)
    }
}