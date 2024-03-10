package com.system.traffic.presentation.screen.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.system.traffic.R
import com.system.traffic.presentation.component.BannersAds
import com.system.traffic.presentation.component.StationInfo
import com.system.traffic.presentation.screen.station.StationViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(Unit){
        stationViewModel.getLikeStationList()
    }

    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    BackHandler(
        enabled = true,
        onBack = {
            (context as Activity).finish()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TitleComponent(
            title = stringResource(R.string.station)
        )

        HorizontalDivider(
            modifier = Modifier.padding(16.dp),
        )

        if(likeStationList.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .weight(0.9f)
            ) {
                items(likeStationList.size){ index ->
                    StationInfo(
                        stationModel = likeStationList[index],
                        stationViewModel = stationViewModel,
                        navHostController = navHostController,
                    )
                }
            }
        }else {
            NoLikeContent(
                modifier = Modifier
                    .weight(0.9f)
            )
        }

        BannersAds(
            modifier = Modifier
                .weight(0.1f)
        )
    }
}

@Composable
fun NoLikeContent(
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.like_no_data),
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
            .padding(16.dp),
        color = Color.Black
    )
}