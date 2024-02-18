package com.system.traffic.presentation.screen.splash

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.system.traffic.domain.dataModel.toLineEntity
import com.system.traffic.domain.dataModel.toStationEntity
import com.system.traffic.navigation.Graph
import com.system.traffic.presentation.screen.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
    commonViewModel: CommonViewModel = hiltViewModel(),
){
    Text(text = "스플래시 화면")


    val stationScope = rememberCoroutineScope()
    val lineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        val a = commonViewModel.checkIsFirstLogin()
        if(a){
            navHostController.popBackStack()
            navHostController.navigate(Graph.HOME)
        }else{
            commonViewModel.setUpIsFirstLogin()

            stationScope.launch(Dispatchers.IO) {
                val stationList = splashViewModel.getFileStationData()
                for(item in stationList){
                    splashViewModel.insertStationInfo(item.toStationEntity())
                }
            }

            lineScope.launch(Dispatchers.IO){
                val lineList = splashViewModel.getFileLineData()
                for(item in lineList){
                    splashViewModel.insertLineInfo(item.toLineEntity())
                }
            }

            navHostController.popBackStack()
            navHostController.navigate(Graph.HOME)
        }
    }
}