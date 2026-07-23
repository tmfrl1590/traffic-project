package com.traffic.presentation.screens.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.design.R
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.domain.model.StationModel
import com.traffic.presentation.screens.home.EmptyLikeStation

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
            LazyColumn(
                modifier = modifier
            ) {
                items(likeStationList) { stationModel ->
                    StationCard(
                        stationModel = stationModel,
                        onStationCardClick = onStationCardClick,
                        onClickFavorite = onClickFavorite,
                    )
                }
            }
        }
        else -> EmptyLikeStation(
            modifier = modifier,
            onGotoStation = onGotoStation,
        )
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
            .fillMaxWidth(),
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
                        .height(52.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TrafficTheme.colors.textPrimary
                )

                Text(
                    text = "${stationModel.nextBusStop} | ${stationModel.arsId}",
                    modifier = Modifier
                        .weight(5f)
                    ,
                    color = TrafficTheme.colors.textPrimary
                )
            }
        }
    }
}