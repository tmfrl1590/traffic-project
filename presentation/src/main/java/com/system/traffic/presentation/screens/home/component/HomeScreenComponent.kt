package com.system.traffic.presentation.screens.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.traffic.design.R
import com.system.traffic.design.component.noRippleClickable
import com.system.traffic.design.ui.theme.MainColor
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.domain.model.StationModel
import com.system.traffic.presentation.screens.home.HomeTestTags

@Composable
fun LikeStationSection(
    modifier: Modifier = Modifier,
    likeStationList: List<StationModel>,
    onStationCardClick: (String, String) -> Unit,
    onClickFavorite: (StationModel) -> Unit,
    onGotoStation: () -> Unit,
) {
    when {
        likeStationList.isNotEmpty() -> {
            LikeStationList(
                likeStationList = likeStationList,
                onStationCardClick = onStationCardClick,
                onClickFavorite = onClickFavorite,
            )
        }
        else -> EmptyLikeStation(
            modifier = modifier,
            onGotoStation = onGotoStation,
        )
    }
}

@Composable
private fun LikeStationList(
    likeStationList: List<StationModel>,
    onStationCardClick: (String, String) -> Unit,
    onClickFavorite: (StationModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .testTag(HomeTestTags.LIKE_STATION_LIST)
    ) {
        itemsIndexed(
            items = likeStationList,
            key = { index, item ->
                "${item.busStopId}_$index"
            }
        ) { index, station ->
            StationCard(
                stationModel = station,
                onStationCardClick = onStationCardClick,
                onClickFavorite = onClickFavorite,
            )
        }
    }
}

@Composable
private fun StationCard(
    stationModel: StationModel,
    onStationCardClick: (String, String) -> Unit,
    onClickFavorite: (StationModel) -> Unit,
){
    Card(
        onClick = {
            onStationCardClick(
                stationModel.arsId ?: "",
                stationModel.busStopId ?: "",
            )
        },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth()
            .testTag(HomeTestTags.STATION_CARD)
        ,
        border = BorderStroke(1.dp, color = TrafficTheme.colors.cardBorder),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = TrafficTheme.colors.cardBackground
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
            ,
        ){
            IconButton(
                onClick = {
                    onClickFavorite(stationModel)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .testTag(HomeTestTags.STATION_CARD_FAVORITE)
            ){
                Icon(
                    painter = painterResource(id = if (stationModel.selected) R.drawable.icon_selected_star else R.drawable.icon_unselected_star),
                    contentDescription = "Favorite",
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stationModel.busStopName ?: "",
                    modifier = Modifier
                        .height(52.dp)
                    ,
                    style = TrafficTheme.typography.cardTitle,
                    color = TrafficTheme.colors.textPrimary
                )

                Text(
                    text = "${stationModel.nextBusStop} | ${stationModel.arsId}",
                    modifier = Modifier
                        .weight(5f)
                    ,
                    style = TrafficTheme.typography.cardBody,
                    color = TrafficTheme.colors.textPrimary
                )
            }
        }
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
            .testTag(HomeTestTags.EMPTY_LIKE)
        ,
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(R.string.like_no_data),
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
                style = TrafficTheme.typography.empty,
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