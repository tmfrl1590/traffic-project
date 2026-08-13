package com.system.traffic.domain.repository

import com.system.traffic.core.enums.AppFontSize
import com.system.traffic.core.enums.AppThemeType
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUpIsFirstLogin()

    suspend fun getIsFirstLogin(): Boolean

    suspend fun setAppFontSize(fontSize: AppFontSize)

    fun getAppFontSize(): Flow<AppFontSize>

    suspend fun setAppThemeType(themeType: AppThemeType)

    fun getAppThemeType(): Flow<AppThemeType>
}