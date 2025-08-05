package com.traffic.splash.component

import com.traffic.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

suspend fun saveStationAndLineJsonData(
    splashViewModel: SplashViewModel,
){
    CoroutineScope(Dispatchers.IO).launch {
        splashViewModel.updateLoadingState("정류장 정보를 불러오는 중...", 0.1f)
        val stationList = splashViewModel.getFileStationData()

        splashViewModel.updateLoadingState("정류장 정보를 저장하는 중...", 0.2f)
        stationList.forEachIndexed { index, station ->
            splashViewModel.insertStationInfo(station)
            val progress = 0.2f + (index.toFloat() / stationList.size) * 0.4f
            if (index % 100 == 0) { // 100개마다 진행상황 업데이트
                splashViewModel.updateLoadingState(
                    "정류장 정보 저장 중... (${index + 1}/${stationList.size})",
                    progress
                )
            }
        }

        splashViewModel.updateLoadingState("노선 정보를 불러오는 중...", 0.6f)
        val lineList = splashViewModel.getFileLineData()

        splashViewModel.updateLoadingState("노선 정보를 저장하는 중...", 0.7f)
        lineList.forEachIndexed { index, line ->
            splashViewModel.insertLineInfo(line)
            val progress = 0.7f + (index.toFloat() / lineList.size) * 0.25f
            if (index % 50 == 0) { // 50개마다 진행상황 업데이트
                splashViewModel.updateLoadingState(
                    "노선 정보 저장 중... (${index + 1}/${lineList.size})",
                    progress
                )
            }
        }

        splashViewModel.updateLoadingState("초기화 완료", 1.0f)
        splashViewModel.completeLoading()
    }.join()
}