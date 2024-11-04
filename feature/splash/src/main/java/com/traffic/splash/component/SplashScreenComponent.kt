package com.traffic.splash.component

import com.traffic.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun saveStationAndLineJsonData(
    splashViewModel: SplashViewModel,
){
    val insertStationJob = CoroutineScope(Dispatchers.IO).launch {
        val stationList = splashViewModel.getFileStationData()
        for(item in stationList){
            splashViewModel.insertStationInfo(item)
        }
    }

    val insertLineJob = CoroutineScope(Dispatchers.IO).launch {
        val lineList = splashViewModel.getFileLineData()
        for(item in lineList){
            splashViewModel.insertLineInfo(item)
        }
    }

    joinAll(insertStationJob, insertLineJob)
}