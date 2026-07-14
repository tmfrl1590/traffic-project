package com.traffic.presentation.screens.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.design.CommonTitleComponent
import com.traffic.design.R
import com.traffic.presentation.firebase.ScreenName
import com.traffic.presentation.firebase.TrackScreenView
import com.traffic.domain.model.StationModel
import com.traffic.presentation.screens.home.action.HomeAction
import com.traffic.presentation.screens.home.component.LikeStationSection
import com.traffic.presentation.screens.home.viewmodel.HomeViewModel

@Composable
fun HomeScreenRoute(
    context: Context,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStationCardClick: (String, String) -> Unit,
) {
    TrackScreenView(screenName = ScreenName.Home)

    val state by homeViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit){
        homeViewModel.getLikeStationList()
    }

    BackHandler(
        enabled = true,
        onBack = {
            (context as Activity).finish()
        }
    )

    HomeScreen(
        likeStationList = state.likeStationList,
        onStationCardClick = onStationCardClick,
        onAction = homeViewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    likeStationList: List<StationModel>,
    onStationCardClick: (String, String) -> Unit,
    onAction: (HomeAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CommonTitleComponent(
                title = stringResource(R.string.like_station)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            LikeStationSection(
                modifier = Modifier.weight(0.9f),
                likeStationList = likeStationList,
                onStationCardClick = onStationCardClick,
                onClickFavorite = { onAction(HomeAction.OnClickFavoriteIcon(stationModel = it))}
            )
        }
    }
}

@Composable
fun EmptyLikeStation(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.like_no_data),
            lineHeight = 24.sp,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
    }
}