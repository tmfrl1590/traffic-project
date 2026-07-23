package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
): DataStoreRepository {
    override suspend fun setUpIsFirstLogin() {
        localDataSource.setUpIsFirstLogin()
    }

    override suspend fun getIsFirstLogin(): Boolean {
        return localDataSource.getIsFirstLogin()
    }

    override suspend fun setAppFontSize(fontSize: String) {
        localDataSource.setAppFontSize(fontSize)
    }

    override fun getAppFontSize(): Flow<String> {
        return localDataSource.getAppFontSize()
    }
}