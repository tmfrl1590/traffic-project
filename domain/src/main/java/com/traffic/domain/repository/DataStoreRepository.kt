package com.traffic.domain.repository

interface DataStoreRepository {

    suspend fun setUpIsFirstLogin()

    suspend fun getIsFirstLogin(): Boolean
}