package com.system.traffic.data.impl

import com.system.traffic.data.remote.RemoteDataSource
import com.system.traffic.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): NetworkRepository {

    override val isNetworkConnected: Flow<Boolean> = remoteDataSource.getNetworkStatus()
}