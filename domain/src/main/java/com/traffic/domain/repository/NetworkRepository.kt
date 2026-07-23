package com.traffic.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    val isNetworkConnected: Flow<Boolean>
}