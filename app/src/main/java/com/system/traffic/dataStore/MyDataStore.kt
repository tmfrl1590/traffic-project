package com.system.traffic.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.system.traffic.App

class MyDataStore {

    private val context = App.context()

    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val myDataStore : DataStore<Preferences> = context.dataStore

    private val isFirstLogin = booleanPreferencesKey("isFirstLogin")
    private val arriveColor = stringPreferencesKey("arriveColor")

    private val alarmReloadTime = stringPreferencesKey("alarmReloadTime")

    private val isServiceStation = stringPreferencesKey("isServiceStation")
    private val isServiceLine = stringPreferencesKey("isServiceLine")


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


    // 설정 - 버스도착시간 색상 저장
    suspend fun setArriveColor(color : String) {
        myDataStore.edit { preferences ->
            preferences[arriveColor] = color
        }
    }

    // 설정 - 버스도착시간 색상값 가져오기
    suspend fun getArriveColor() : String {
        var value = ""

        myDataStore.edit { preferences ->
            value = preferences[arriveColor] ?: ""
        }
        return value
    }

    // 현재 실행중인 알람 서비스 값 저장 - 정류장
    suspend fun setAlarmServiceStation(busStopId : String){
        myDataStore.edit { preferences ->
            preferences[isServiceStation] = busStopId
        }
    }

    // 현재 실행중인 알람 서비스 값 저장 - 버스
    suspend fun setAlarmServiceLine(lineName : String){
        myDataStore.edit { preferences ->
            preferences[isServiceLine] = lineName
        }
    }

    // 현재 실행중인 알람 서비스 값 가져오기 - 정류장
    suspend fun getAlarmServiceStation() : String {
        var value = ""
        myDataStore.edit { preferences ->
            value = preferences[isServiceStation] ?: ""
        }
        return value
    }

    // 현재 실행중인 알람 서비스 값 가져오기 - 버스
    suspend fun getAlarmServiceLine() : String {
        var value = ""
        myDataStore.edit { preferences ->
            value = preferences[isServiceLine] ?: ""
        }
        return value
    }

    // 알람 갱신시간 세팅
    suspend fun setAlarmReloadTime(reloadTime : String){
        myDataStore.edit { preferences ->
            preferences[alarmReloadTime] = reloadTime
        }
    }

    // 알람 갱신시간 값 가져오기
    suspend fun getAlarmReloadTime() : String {
        var value = ""
        myDataStore.edit { preferences ->
            value = preferences[alarmReloadTime] ?: ""
        }
        return value
    }
}