package com.traffic.presentation.screens.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.design.CommonTitleComponent
import com.traffic.design.R
import com.traffic.design.noRippleClickable
import com.traffic.design.ui.theme.MainColor
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.domain.model.StationModel
import com.traffic.presentation.firebase.ScreenName
import com.traffic.presentation.firebase.TrackScreenView
import com.traffic.presentation.screens.home.action.HomeAction
import com.traffic.presentation.screens.home.component.LikeStationSection
import com.traffic.presentation.screens.home.viewmodel.HomeViewModel

@Composable
fun HomeScreenRoute(
    context: Context,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStationCardClick: (String, String) -> Unit,
    onGotoStation: () -> Unit,
) {
    TrackScreenView(screenName = ScreenName.Home)

    val state by homeViewModel.state.collectAsStateWithLifecycle()

    BackHandler(
        enabled = true,
        onBack = {
            (context as Activity).finish()
        }
    )

    HomeScreen(
        likeStationList = state.likeStationList,
        onStationCardClick = onStationCardClick,
        onAction = homeViewModel::onAction,
        onGotoStation = onGotoStation,
    )
}

@Composable
private fun HomeScreen(
    likeStationList: List<StationModel>,
    onStationCardClick: (String, String) -> Unit,
    onAction: (HomeAction) -> Unit,
    onGotoStation: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TrafficTheme.colors.mainBackground)
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
            onClickFavorite = { onAction(HomeAction.OnClickFavoriteIcon(stationModel = it))},
            onGotoStation = onGotoStation,
        )
    }
}

@Composable
fun EmptyLikeStation(
    modifier: Modifier = Modifier,
    onGotoStation: () -> Unit,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(R.string.like_no_data),
                lineHeight = 24.sp,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = TrafficTheme.colors.textPrimary,
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Text(
                text = stringResource(R.string.home_empty_action_search),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MainColor,
                modifier = Modifier
                    .noRippleClickable { onGotoStation() }
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun PreviewHomeScreenLight() {
    TrafficTheme(darkTheme = false) {
        HomeScreen(
            likeStationList = listOf(
                StationModel(
                    stationNum = "1",
                    busStopName = "강남역",
                    nextBusStop = "신논현역",
                    busStopId = "10001",
                    arsId = "22001",
                    longitude = "",
                    latitude = ""
                )
            ),
            onStationCardClick = { _, _ -> },
            onAction = {},
            onGotoStation = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
private fun PreviewHomeScreenDark() {
    TrafficTheme(darkTheme = true) {
        HomeScreen(
            likeStationList = listOf(
                StationModel(
                    stationNum = "1",
                    busStopName = "강남역",
                    nextBusStop = "신논현역",
                    busStopId = "10001",
                    arsId = "22001",
                    longitude = "",
                    latitude = ""
                )
            ),
            onStationCardClick = { _, _ -> },
            onAction = {},
            onGotoStation = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty Like Station Light Mode")
@Composable
private fun PreviewEmptyLikeStation() {
    TrafficTheme(darkTheme = false) {
        HomeScreen(
            likeStationList = emptyList(),
            onStationCardClick = { _, _ -> },
            onAction = {},
            onGotoStation = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty Like Station Dark Mode")
@Composable
private fun PreviewEmptyLikeStationDark() {
    TrafficTheme(darkTheme = true) {
        HomeScreen(
            likeStationList = emptyList(),
            onStationCardClick = { _, _ -> },
            onAction = {},
            onGotoStation = {}
        )
    }
}