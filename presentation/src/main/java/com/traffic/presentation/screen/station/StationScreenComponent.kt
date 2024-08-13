package com.traffic.presentation.screen.station

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.silver.navigation.Screens
import com.traffic.domain.model.StationModel
import com.traffic.presentation.R
import com.traffic.presentation.screen.component.NoDataComponent
import com.traffic.presentation.ui.theme.MainColor

@Composable
fun StationListArea(
    modifier: Modifier = Modifier,
    stationViewModel: StationViewModel,
    navHostController: NavHostController,
    searchedStationList: List<StationModel>,
) {
    if (searchedStationList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ){
            itemsIndexed(
                items = searchedStationList,
                key = { index, _ ->
                    index
                }
            ){_, item ->
                SearchedStationInfo(
                    stationModel = item,
                    stationViewModel = stationViewModel,
                    navHostController = navHostController,
                )
            }
        }
    } else {
        NoDataComponent(
            modifier = modifier,
            text = stringResource(R.string.searched_station_no_data)
        )
    }

    /*BannersAds(
        modifier = Modifier
            .weight(0.1f)
    )*/
}

@Composable
private fun SearchedStationInfo(
    stationModel: StationModel,
    stationViewModel: StationViewModel,
    navHostController: NavHostController,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navHostController.navigate(Screens.BusArrive(arsId = stationModel.busStopId ?: ""))
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            StationInfoFavoriteIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                stationModel = stationModel,
                stationViewModel = stationViewModel,
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                BusStationName(
                    stationModel = stationModel
                )

                CurrentBusStopNameAndArsId(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    stationModel = stationModel
                )
            }
        }
    }
}

@Composable
private fun StationInfoFavoriteIcon(
    modifier: Modifier,
    stationModel: StationModel,
    stationViewModel: StationViewModel,
) {
    IconButton(
        onClick = {
            insertOrDeleteStationInfo(
                stationModel = stationModel,
                stationViewModel = stationViewModel
            )
        },
        modifier = modifier
    ){
        Icon(
            imageVector = if(stationModel.selected) Icons.Default.Favorite else Icons.Default.FavoriteBorder ,
            contentDescription = "Favorite",
            tint = MainColor
        )
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

@Composable
private fun BusStationName(
    stationModel: StationModel,
) {
    Text(
        text = stationModel.busStopName ?: "",
        modifier = Modifier
            .height(52.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun CurrentBusStopNameAndArsId(
    modifier: Modifier,
    stationModel: StationModel,
) {
    Text(
        text = currentBusStopNameAndArsId(stationModel),
        modifier = modifier
    )
}

private fun currentBusStopNameAndArsId(stationModel: StationModel): String {
    return "${stationModel.nextBusStop} | ${stationModel.arsId}"
}