package com.system.traffic.domain.usecase.datastore

import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class SetAppThemeTypeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(themeType: AppThemeType) {
        dataStoreRepository.setAppThemeType(themeType = themeType)
    }
}