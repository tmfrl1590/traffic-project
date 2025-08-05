package com.system.traffic.local.db

import android.content.Context
import com.traffic.data.model.local.LineEntity
import com.traffic.data.model.local.StationEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

class FileDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
){
    private val stationList = ArrayList<StationEntity>()
    private val lineList = ArrayList<LineEntity>()

    fun getStationDataFromFile(): List<StationEntity> {

        val jsonString = context.assets.open("station.json").reader().readText()
        val resultList = JSONObject(jsonString).getJSONArray("STATION_LIST")

        for(i in 0 until resultList.length()){
            val jsonObject = resultList.getJSONObject(i)
            val stationNum = jsonObject.getString("STATION_NUM").toString()
            val busStopName = jsonObject.getString("BUSSTOP_NAME").toString()
            val arsId = jsonObject.getString("ARS_ID").toString()
            val nextBusStop = jsonObject.getString("NEXT_BUSSTOP").toString()
            val busStopId = jsonObject.getString("BUSSTOP_ID").toString()
            val longitude = jsonObject.getString("LONGITUDE").toString()
            val latitude = jsonObject.getString("LATITUDE").toString()

            val station = StationEntity(stationNum, busStopName, nextBusStop, busStopId, arsId, longitude, latitude)
            stationList.add(station)
        }
        return stationList
    }

    fun getLineDataFromFile(): List<LineEntity>{
        val jsonString = context.assets.open("line.json").reader().readText()
        val resultList = JSONObject(jsonString).getJSONArray("LINE_LIST")

        for(i in 0 until resultList.length()){
            val jsonObject = resultList.getJSONObject(i)
            val dirDownName = jsonObject.getString("DIR_DOWN_NAME").toString()
            val runInterval = jsonObject.getString("RUN_INTERVAL").toString()
            val lastRunTime = jsonObject.getString("LAST_RUN_TIME").toString()
            val lineNum = jsonObject.getString("LINE_NUM").toString()
            val firstRunTime = jsonObject.getString("FIRST_RUN_TIME").toString()
            val dirUpName = jsonObject.getString("DIR_UP_NAME").toString()
            val lineId = jsonObject.getString("LINE_ID").toString()
            val lineKind = jsonObject.getString("LINE_KIND").toString()
            val lineName = jsonObject.getString("LINE_NAME").toString()

            val line = LineEntity(dirDownName, runInterval, lastRunTime, lineNum, firstRunTime, dirUpName, lineId, lineKind, lineName, selected = false)
            lineList.add(line)
        }
        return lineList
    }
}