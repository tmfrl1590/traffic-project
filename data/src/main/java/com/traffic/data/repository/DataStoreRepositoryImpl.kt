package com.traffic.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.traffic.common.Constants.IS_FIRST_LOGIN
import com.traffic.common.Constants.TRAFFIC_PREFERENCES
import com.traffic.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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
}