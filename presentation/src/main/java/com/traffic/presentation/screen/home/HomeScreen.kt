package com.traffic.presentation.screen.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.traffic.common.firebase.logEvent
import com.traffic.presentation.R
import com.traffic.presentation.screen.component.CommonTitleComponent
import com.traffic.presentation.screen.station.StationViewModel

@Composable
fun HomeScreen(
    context: Context,
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit){
        stationViewModel.getLikeStationList()
        logEvent(context, "HomeScreen")
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
        CommonTitleComponent(
            title = stringResource(R.string.like_station)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.padding(bottom = 16.dp))

        LikeStationArea(
            modifier = Modifier.weight(0.9f),
            stationViewModel = stationViewModel,
            navHostController = navHostController,
            likeStationList = likeStationList,
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