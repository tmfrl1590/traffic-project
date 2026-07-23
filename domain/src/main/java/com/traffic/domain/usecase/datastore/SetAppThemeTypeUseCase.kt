package com.traffic.domain.usecase.datastore

import com.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetAppThemeTypeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(themeType: String) {
        dataStoreRepository.setAppThemeType(themeType = themeType)
    }
}