package com.system.traffic.local.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.system.traffic.local.DataStoreConstants
import com.system.traffic.local.db.FileDataSource
import com.system.traffic.local.db.dao.KeywordDao
import com.system.traffic.local.db.dao.LikeStationDao
import com.system.traffic.local.db.dao.LineDao
import com.system.traffic.local.db.dao.StationDao
import com.system.traffic.local.db.model.KeywordLocal
import com.system.traffic.local.db.model.LineLocal
import com.system.traffic.local.db.model.StationLocal
import com.system.traffic.local.db.model.toLikeStationEntity
import com.system.traffic.local.db.model.toLikeStationModel
import com.system.traffic.local.toData
import com.traffic.data.local.LocalDataSource
import com.traffic.data.model.local.KeywordEntity
import com.traffic.data.model.local.LineEntity
import com.traffic.data.model.local.StationEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreConstants.TRAFFIC_PREFERENCES)

class LocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val likeStationDao: LikeStationDao,
    private val stationDao: StationDao,
    private val lineDao: LineDao,
    private val keywordDao: KeywordDao,
    private val fileDataSource: FileDataSource,
): LocalDataSource {

    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("pref")
    }

    private val myDataStore : DataStore<Preferences> = context.dataStore

    private val isFirstLogin = booleanPreferencesKey(DataStoreConstants.IS_FIRST_LOGIN)

    override suspend fun setUpIsFirstLogin() {
        myDataStore.edit { preferences ->
            preferences[isFirstLogin] = false
        }
    }

    override suspend fun getIsFirstLogin(): Boolean {
        val preferences = myDataStore.data.first()
        return preferences[isFirstLogin] ?: true
    }

    override suspend fun addLikeStation(stationEntity: StationEntity) {
        likeStationDao.addLikeStation(likeStationLocal = stationEntity.toLikeStationEntity())
    }

    override suspend fun deleteLikeStation(arsId: String) {
        likeStationDao.deleteLikeStation(arsId = arsId)
    }

    override fun getLikeStationList(): Flow<List<StationEntity>> {
        return likeStationDao.getLikeStationList().map { list ->
            list.map { it.toLikeStationModel() }
        }
    }

    override fun getSearchedStationList(keyword: String): List<StationEntity> {
        return stationDao.getSearchedStationList("%$keyword%")
            .map { it.toLikeStationModel() }
    }

    override fun getStationInfo(arsId: String): Flow<StationEntity> {
        return stationDao.getStationInfo(arsId).map { stationLocal ->
            stationLocal?.toLikeStationModel() ?: StationEntity(
                stationNum = "",
                busStopName = "정류장 정보 없음",
                nextBusStop = "",
                busStopId = "",
                arsId = arsId,
                longitude = "",
                latitude = ""
            )
        }
    }

    override fun getStationFileData(): List<StationEntity> {
        return fileDataSource.getStationDataFromFile()
    }

    override fun insertStation(stationEntity: StationEntity) {
        val stationLocal = StationLocal(
            stationNum = stationEntity.stationNum,
            busStopName = stationEntity.busStopName,
            nextBusStop = stationEntity.nextBusStop,
            busStopId = stationEntity.busStopId,
            arsId = stationEntity.arsId,
            longitude = stationEntity.longitude,
            latitude = stationEntity.latitude
        )
        stationDao.insertStation(stationLocal)
    }

    override fun getLineFileData(): List<LineEntity> {
        return fileDataSource.getLineDataFromFile()
    }

    override fun insertLine(lineEntity: LineEntity) {
        val lineLocal = LineLocal(
            line_id = lineEntity.line_id,
            dir_down_name = lineEntity.dir_down_name,
            run_interval = lineEntity.run_interval,
            last_run_time = lineEntity.last_run_time,
            line_num = lineEntity.line_num,
            first_run_time = lineEntity.first_run_time,
            dir_up_name = lineEntity.dir_up_name,
            line_kind = lineEntity.line_kind,
            line_name = lineEntity.line_name,
            selected = lineEntity.selected
        )
        lineDao.insertLine(lineLocal)
    }

    override suspend fun insertKeyword(keyword: String) {
        val keywordLocal = KeywordLocal(
            keyword = keyword
        )
        keywordDao.insertKeyword(keywordLocal)
    }

    override fun getKeywordList(): Flow<List<KeywordEntity>> {
        return keywordDao.getKeywordList().map { it.toData() }
    }
}