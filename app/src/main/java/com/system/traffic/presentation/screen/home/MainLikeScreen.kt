package com.system.traffic.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.system.traffic.presentation.screen.CommonViewModel
import com.system.traffic.presentation.screen.station.StationInfo
import com.system.traffic.presentation.screen.station.StationViewModel

@Composable
fun MainLikeScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
    commonViewModel: CommonViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "정류장", fontSize = 20.sp)
            Spacer(modifier = Modifier.size(10.dp))
            Divider()
            Spacer(modifier = Modifier.size(10.dp))

            if(likeStationList.isNotEmpty()){
                LazyColumn(){
                    items(likeStationList.size){ index ->
                        StationInfo(
                            stationEntity = likeStationList[index],
                            stationViewModel = stationViewModel,
                            commonViewModel = commonViewModel,
                            navHostController = navHostController
                        )
                    }
                }
            }else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "즐겨찾기 정보가\n없습니다",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "정류장", fontSize = 20.sp)
            Spacer(modifier = Modifier.size(10.dp))
            Divider()
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){

            }
        }
    }
}