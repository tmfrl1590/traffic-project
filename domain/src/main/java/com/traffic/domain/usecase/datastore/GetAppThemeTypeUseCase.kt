package com.traffic.domain.usecase.datastore

import com.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetAppThemeTypeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.getAppThemeType()
}