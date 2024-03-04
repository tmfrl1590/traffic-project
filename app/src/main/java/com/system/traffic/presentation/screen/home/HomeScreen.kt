package com.system.traffic.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.system.traffic.presentation.component.StationInfo
import com.system.traffic.presentation.screen.station.StationViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit){
        stationViewModel.getLikeStationList()
    }

    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TitleComponent(
            title = "정류장"
        )

        Divider()

        Spacer(modifier = Modifier.size(8.dp))

        if(likeStationList.isNotEmpty()){
            LazyColumn {
                items(likeStationList.size){ index ->
                    StationInfo(
                        stationModel = likeStationList[index],
                        stationViewModel = stationViewModel,
                        navHostController = navHostController,
                    )
                }
            }
        }else {
            NoLikeContent()
        }
    }
}

@Composable
fun NoLikeContent(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "즐겨찾기 정보가\n없습니다",
            lineHeight = 24.sp,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun TitleComponent(
    title: String,
){
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(12.dp)
    )
}