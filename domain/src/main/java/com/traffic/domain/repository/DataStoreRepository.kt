package com.traffic.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUpIsFirstLogin()

    suspend fun getIsFirstLogin(): Boolean

    suspend fun setAppFontSize(fontSize: String)

    fun getAppFontSize(): Flow<String>
}