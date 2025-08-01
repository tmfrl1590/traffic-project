package com.traffic.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
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
import androidx.navigation.NavHostController
import com.traffic.common.R
import com.traffic.domain.model.StationModel
import com.traffic.station.viewmodel.StationViewModel

@Composable
fun LikeStationArea(
    modifier: Modifier = Modifier,
    likeStationList: List<StationModel>,
    stationViewModel: StationViewModel,
    onStationCardClick: (String) -> Unit,
) {
    if(likeStationList.isNotEmpty()){
        LazyColumn(
            modifier = modifier
        ) {
            items(likeStationList.size){ index ->
                StationInfo(
                    stationModel = likeStationList[index],
                    stationViewModel = stationViewModel,
                    onStationCardClick = onStationCardClick
                )
            }
        }
    }else {
        NoLikeContent(
            modifier = modifier
        )
    }
}

@Composable
private fun StationInfo(
    stationModel: StationModel,
    stationViewModel: StationViewModel,
    onStationCardClick: (String) -> Unit,
){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onStationCardClick(stationModel.busStopId ?: "")
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            IconButton(
                onClick = {
                    insertOrDeleteStationInfo(
                        stationModel = stationModel,
                        stationViewModel = stationViewModel
                    )
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
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${stationModel.nextBusStop} | ${stationModel.arsId}",
                    modifier = Modifier
                        .weight(5f)
                )
            }
        }
    }
}

private fun insertOrDeleteStationInfo(
    stationModel: StationModel,
    stationViewModel: StationViewModel,
){
    if(stationModel.selected){
        stationViewModel.deleteLikeStation(stationModel.busStopId ?: "")
    }else {
        stationViewModel.insertLikeStation(stationModel)
    }
}