package com.system.traffic.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.system.traffic.R
import com.system.traffic.data.local.db.entity.toLineEntity
import com.system.traffic.data.local.db.entity.toStationEntity
import com.system.traffic.navigation.Graph
import com.system.traffic.presentation.screen.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
    commonViewModel: CommonViewModel = hiltViewModel(),
){
    Image(
        modifier = Modifier
            .width(12.dp)
            .height(12.dp),
        painter = painterResource(id = R.drawable.main_bus),
        contentDescription = ""
    )

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