package com.system.traffic.data.impl

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.data.local.LocalDataSource
import com.system.traffic.domain.repository.DataStoreRepository
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

    override suspend fun setAppFontSize(fontSize: AppFontSize) {
        localDataSource.setAppFontSize(fontSize)
    }

    override fun getAppFontSize(): Flow<AppFontSize> {
        return localDataSource.getAppFontSize()
    }

    override suspend fun setAppThemeType(themeType: AppThemeType) {
        localDataSource.setAppThemeType(themeType)
    }

    override fun getAppThemeType(): Flow<AppThemeType> {
        return localDataSource.getAppThemeType()
    }
}