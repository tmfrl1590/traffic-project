package com.system.traffic.data.local.dataSource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.system.traffic.App
import com.system.traffic.common.Constants.IS_FIRST_LOGIN

class DataStore {

    private val context = App.context()

    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("pref")
    }

    private val myDataStore : DataStore<Preferences> = context.dataStore

    private val isFirstLogin = booleanPreferencesKey(IS_FIRST_LOGIN)

    suspend fun setUpIsFirstLogin(){
        myDataStore.edit { preferences ->
            preferences[isFirstLogin] = true
        }
    }

    suspend fun checkIsFirstLogin() : Boolean {
        var value = false

        myDataStore.edit { preferences ->
            value = preferences[isFirstLogin] ?: false
        }
        return value
    }
}