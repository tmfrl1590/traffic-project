package com.system.traffic.local.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.system.traffic.core.enum.AppFontSize
import com.system.traffic.core.enum.AppThemeType
import com.system.traffic.local.DataStoreConstants
import com.system.traffic.local.db.FileDataSource
import com.system.traffic.local.db.dao.KeywordDao
import com.system.traffic.local.db.dao.LikeStationDao
import com.system.traffic.local.db.dao.LineDao
import com.system.traffic.local.db.dao.PinnedBusDao
import com.system.traffic.local.db.dao.StationDao
import com.system.traffic.local.db.model.KeywordLocal
import com.system.traffic.local.db.model.LineLocal
import com.system.traffic.local.db.model.PinnedBusLocal
import com.system.traffic.local.db.model.StationLocal
import com.system.traffic.local.db.model.toLikeStationEntity
import com.system.traffic.local.db.model.toLikeStationModel
import com.system.traffic.local.toData
import com.traffic.data.local.LocalDataSource
import com.traffic.data.model.local.KeywordEntity
import com.traffic.data.model.local.LineEntity
import com.traffic.data.model.local.PinnedBusEntity
import com.traffic.data.model.local.StationCoordinates
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
    private val pinnedBusDao: PinnedBusDao,
    private val fileDataSource: FileDataSource,
): LocalDataSource {

    private val myDataStore : DataStore<Preferences> = context.dataStore

    private val isFirstLogin = booleanPreferencesKey(DataStoreConstants.IS_FIRST_LOGIN)
    private val appFontSize = stringPreferencesKey(DataStoreConstants.APP_FONT_SIZE)
    private val appThemeType = stringPreferencesKey(DataStoreConstants.APP_THEME_TYPE)

    override suspend fun setUpIsFirstLogin() {
        myDataStore.edit { preferences ->
            preferences[isFirstLogin] = false
        }
    }

    override suspend fun getIsFirstLogin(): Boolean {
        val preferences = myDataStore.data.first()
        return preferences[isFirstLogin] ?: true
    }

    override suspend fun setAppFontSize(fontSize: String) {
        val enumName = AppFontSize.fromFontSizeText(fontSize).name
        myDataStore.edit { preferences ->
            preferences[appFontSize] = enumName
        }
    }

    override fun getAppFontSize(): Flow<String> {
        return myDataStore.data.map { preferences ->
            val name = preferences[appFontSize] ?: AppFontSize.MEDIUM.name
            val appFontSize = runCatching { AppFontSize.valueOf(name) }.getOrDefault(defaultValue = AppFontSize.MEDIUM)
            appFontSize.fontSizeText
        }
    }

    override suspend fun setAppThemeType(themeType: String) {
        val enumName = AppThemeType.fromThemeName(themeType).name
        myDataStore.edit { preferences ->
            preferences[appThemeType] = enumName
        }
    }

    override fun getAppThemeType(): Flow<String> {
        return myDataStore.data.map { preferences ->
            val name = preferences[appThemeType] ?: AppThemeType.LIGHT.name
            val appThemeType = runCatching { AppThemeType.valueOf(name) }.getOrDefault(defaultValue = AppThemeType.LIGHT)
            appThemeType.themeName
        }
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

    override suspend fun getSearchedStationList(keyword: String): List<StationEntity> {
        return stationDao.getSearchedStationList("%$keyword%")
            .map { it.toLikeStationModel() }
    }

    override suspend fun getStationInfo(arsId: String): StationEntity {
        return stationDao.getStationInfo(arsId)?.toLikeStationModel() ?: StationEntity(
            stationNum = "",
            busStopName = "정류장 정보 없음",
            nextBusStop = "",
            busStopId = "",
            arsId = arsId,
            longitude = "",
            latitude = ""
        )
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

    override fun insertStations(stations: List<StationEntity>) {
        val stationLocals = stations.map { entity ->
            StationLocal(
                stationNum = entity.stationNum,
                busStopName = entity.busStopName,
                nextBusStop = entity.nextBusStop,
                busStopId = entity.busStopId,
                arsId = entity.arsId,
                longitude = entity.longitude,
                latitude = entity.latitude
            )
        }
        stationDao.insertAllStations(stationLocals)
    }

    override fun getLineFileData(): List<LineEntity> {
        return fileDataSource.getLineDataFromFile()
    }

    override fun insertLine(lineEntity: LineEntity) {
        val lineLocal = LineLocal(
            line_id = lineEntity.lineId,
            dir_down_name = lineEntity.dirDownName,
            run_interval = lineEntity.runInterval,
            last_run_time = lineEntity.lastRunTime,
            line_num = lineEntity.lineNum,
            first_run_time = lineEntity.firstRunTime,
            dir_up_name = lineEntity.dirUpName,
            line_kind = lineEntity.lineKind,
            line_name = lineEntity.lineName,
            selected = lineEntity.selected
        )
        lineDao.insertLine(lineLocal)
    }

    override fun insertLines(lines: List<LineEntity>) {
        val lineLocals = lines.map { entity ->
            LineLocal(
                line_id = entity.lineId,
                dir_down_name = entity.dirDownName,
                run_interval = entity.runInterval,
                last_run_time = entity.lastRunTime,
                line_num = entity.lineNum,
                first_run_time = entity.firstRunTime,
                dir_up_name = entity.dirUpName,
                line_kind = entity.lineKind,
                line_name = entity.lineName,
                selected = entity.selected
            )
        }
        lineDao.insertAllLines(lineLocals)
    }

    override suspend fun insertKeyword(keyword: String) {
        val trimmed = keyword.trim()
        if (trimmed.isEmpty()) return
        keywordDao.insertKeyword(KeywordLocal(keyword = trimmed))
    }

    override fun getKeywordList(): Flow<List<KeywordEntity>> {
        return keywordDao.getKeywordList().map { it.toData() }
    }

    override suspend fun deleteKeyword(keyword: String) {
        keywordDao.deleteKeyword(keyword = keyword)
    }

    override suspend fun allDeleteKeyword() {
        keywordDao.allDeleteKeyword()
    }

    override suspend fun getLocationInfo(ids: List<String?>): List<StationCoordinates> {
        return stationDao.getStationsByIds(ids = ids)
    }

    override suspend fun addPinnedBus(busStopId: String, lineId: String) {
        val pinnedBusLocal = PinnedBusLocal(
            busStopId = busStopId,
            lineId = lineId,
            createdAt = System.currentTimeMillis()
        )
        pinnedBusDao.insertPinnedBus(pinnedBusLocal = pinnedBusLocal)
    }

    override suspend fun deletePinnedBus(busStopId: String, lineId: String) {
        pinnedBusDao.deletePinnedBus(busStopId = busStopId, lineId = lineId)
    }

    override fun getPinnedBusList(busStopId: String): Flow<List<PinnedBusEntity>> {
        return pinnedBusDao.getPinnedBus(busStopId = busStopId).map { it.toData() }
    }

    override suspend fun resetPinnedBus() {
        pinnedBusDao.resetPinnedBus()
    }
}