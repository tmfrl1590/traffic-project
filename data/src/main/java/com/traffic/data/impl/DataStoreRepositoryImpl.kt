package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.domain.repository.DataStoreRepository
import javax.inject.Inject

/*
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TRAFFIC_PREFERENCES)

class DataStoreRepositoryImpl @Inject constructor(
    context: Context
): DataStoreRepository{
    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("pref")
    }

    private val myDataStore : DataStore<Preferences> = context.dataStore

    private val isFirstLogin = booleanPreferencesKey(IS_FIRST_LOGIN)

    override suspend fun setUpIsFirstLogin() {
        myDataStore.edit { preferences ->
            preferences[isFirstLogin] = false
        }
    }

    override suspend fun getIsFirstLogin(): Boolean {

        var value = false

        myDataStore.edit { preferences ->
            value = preferences[isFirstLogin] ?: true
        }
        return value
    }
}*/


class DataStoreRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
): DataStoreRepository {
    override suspend fun setUpIsFirstLogin() {
        localDataSource.setUpIsFirstLogin()
    }

    override suspend fun getIsFirstLogin(): Boolean {
        return localDataSource.getIsFirstLogin()
    }
}