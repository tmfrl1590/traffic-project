package com.system.traffic.main

import android.content.Intent
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.BusArriveActivity

interface Handler1 {

    fun onStationClick(stationEntity: StationEntity)

    fun onLineClick(lineEntity: LineEntity)

    fun intentBusArriveActivity(stationEntity: StationEntity)

    fun intentLineStationActivity(lineEntity: LineEntity)
}