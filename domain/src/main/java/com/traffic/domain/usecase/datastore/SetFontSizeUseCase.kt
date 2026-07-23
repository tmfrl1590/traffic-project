package com.traffic.domain.usecase.datastore

import com.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetFontSizeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(fontSizeText: String) {
        dataStoreRepository.setAppFontSize(fontSize = fontSizeText)
    }
}